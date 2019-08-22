package controller;

import model.DataModel;
import org.apache.poi.ss.usermodel.Row;
import view.GUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelWorker extends SwingWorker<Void, Integer> {

    private GUI parent;
    private DataModel dm;
    private int total;

    private FolderBrowser browser = new FolderBrowser();

    private ArrayList<File> imagesList = new ArrayList<>();
    private HashMap<String, Row> rows = new HashMap<>();

    private DecimalFormat df = new DecimalFormat();

    ExcelWorker(GUI parent) {
        this.parent = parent;
        dm = parent.getModel();
    }

    @Override
    protected Void doInBackground() {

        parent.getProgress().setValue(0);
        parent.getProgress().setString("Counting files...");

        browser.recursivelyBrowseFiles(dm.getWorkingDir(), imagesList);
        total = imagesList.size();

        updateExcel();

        return null;
    }

    private void updateExcel() {
        df.setMaximumFractionDigits(2);
        try {
            dm.readExcel(rows);

            for (File f : imagesList) {
                SimpleImageInfo sii = new SimpleImageInfo(f);
                double size = sii.getSize();
                int width = sii.getWidth();
                int height = sii.getHeight();

                String fileName = f.getName().substring(0, f.getName().lastIndexOf("."));

                Row row = rows.get(fileName);

                row.createCell(dm.getColSize()).setCellValue(df.format(size));
                row.createCell(dm.getColWidth()).setCellValue(width);
                row.createCell(dm.getColHeight()).setCellValue(height);
            }

            dm.writeExcel();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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


}