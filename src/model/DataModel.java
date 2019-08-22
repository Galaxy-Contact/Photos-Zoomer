package model;

import java.io.File;

public class DataModel {
    private File workingDir, resultDir, smallDir;
    private int limWidth, limHeight;
    private int ratio;

//    private HashMap<String, Integer> zoomingAlgorithm = new HashMap<>();

    public DataModel() {

    }

    public File getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(File workingDir) {
        this.workingDir = workingDir;
        resultDir = new File(workingDir.getPath() + File.separatorChar + "bonnes photos");
        resultDir.mkdirs();

        smallDir = new File(workingDir.getPath() + File.separatorChar + "trop petite");
        smallDir.mkdirs();
    }

    public int getLimWidth() {
        return limWidth;
    }

    public void setLimWidth(int limWidth) {
        this.limWidth = limWidth;
    }

    public int getLimHeight() {
        return limHeight;
    }

    public void setLimHeight(int limHeight) {
        this.limHeight = limHeight;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public File getResultDir() {
        return resultDir;
    }

    public File getSmallDir() {
        return smallDir;
    }
}