package controller;

import model.DataModel;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import view.GUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ZoomWorker extends SwingWorker<Void, Integer> {

    private GUI parent;
    private DataModel dm;
    private int total;

    private ArrayList<File> imageList = new ArrayList<>();

    public ZoomWorker(GUI parent) {
        this.parent = parent;
        dm = parent.getModel();
    }

    @Override
    protected Void doInBackground() {

        try {
            parent.getProgress().setValue(0);

            parent.getProgress().setString("Counting files...");

            recursivelyBrowseFiles(dm.getWorkingDir());

            total = imageList.size();

            doZoom();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        int current = chunks.get(chunks.size() - 1);
        parent.getProgress().setValue((int) ((float) current / total * 100.0));
        parent.getProgress().setString("Processed " + current + " / " + total);
    }

    @Override
    protected void done() {
        parent.getProgress().setValue(100);
        parent.getProgress().setString("Finished");
    }

    /*
        Browse files in the folder, including sub-directories
        Build a files map
     */

    private void recursivelyBrowseFiles(File cur) {
        if (cur.isFile()) {
            imageList.add(cur);
        } else {
            File[] listFile = cur.listFiles();
            if (listFile == null)
                return;
            for (File f : listFile) {
                recursivelyBrowseFiles(f);
            }
        }
    }

    /*
        Zoom the photos
     */

    private void doZoom() {
        int current = 0;
        for (File fileImage : imageList) {
            zoomAndExport(fileImage, dm.getLimWidth(), dm.getLimHeight());
            current++;
            publish(current);
        }
    }

    /*
        Auto find suitable ratio to zoom the photo.
        This function ensures that the number of pixels is not less than the limit.
     */

    private void zoomAndExport(File input, int limWidth, int limHeight) {
        try {
            Mat src = Imgcodecs.imread(input.getPath());
            Mat dst = new Mat();

            int ratio = calculateSuitableRatio(src.width(), src.height(), limWidth, limHeight, 100, dm.getRatio());

            if (ratio == -1) {

                // Can't found suitable ratio => too small photos

                File destination = new File(dm.getSmallDir().getPath() + File.separatorChar + input.getName());
                Files.move(input.toPath(), destination.toPath());

            } else if (ratio == 100) {

                // Ratio is 100%, no need to resize, move directly to the result folder

                File destination = new File(dm.getResultDir().getPath() + File.separatorChar + input.getName());
                Files.move(input.toPath(), destination.toPath());

            } else {

                // Zoom the photo

                File destination = new File(dm.getResultDir().getPath() + File.separatorChar + input.getName());

                Imgproc.resize(src, dst, new Size(src.width() * ratio / 100.0, src.height() * ratio / 100.0), 0, 0, Imgproc.INTER_CUBIC);

                System.out.println(src.width() + "x" + src.height() + " (" + ratio + " %) => " + dst.width() + "x" + dst.height());
                Imgcodecs.imwrite(destination.getAbsolutePath(), dst);

                input.delete();

            }
            src.release();
            dst.release();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
        This function return the smallest suitable ratio to zoom a photo
        Can use math formula, it's the fastest, but maybe not 100% correct, because of dynamic floating point
        => Binary - search to optimize time and accurate

        @Return -1 if can't found a suitable ratio between minRatio and maxRatio
     */

    private int calculateSuitableRatio(int width, int height, int limWidth, int limHeight, int minRatio, int maxRatio) {
        int res = -1, newWidth, newHeight, mid;
        while (minRatio <= maxRatio) {
            mid = (minRatio + maxRatio) / 2;
            newWidth = (int) (width * mid / 100.0);
            newHeight = (int) (height * mid / 100.0);
            if (newWidth * newHeight >= limWidth * limHeight) {
                res = mid;
                maxRatio = mid - 1;
            } else {
                minRatio = mid + 1;
            }
        }
        return res;
    }
}