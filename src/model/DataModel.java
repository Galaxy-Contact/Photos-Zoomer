package model;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DataModel {
    private File workingDir, resultDir, smallDir, excelFile;
    private int limWidth, limHeight;
    private int ratio;

    private Workbook workbook;

    private int colRef, colSize, colWidth, colHeight;

//    private HashMap<String, Integer> zoomingAlgorithm = new HashMap<>();

    public void readExcel(HashMap<String, Row> rows) throws IOException {
        FileInputStream fis = new FileInputStream(excelFile);
        workbook = new HSSFWorkbook(fis);
        Sheet inputSheet = workbook.getSheetAt(0);
        Iterator<Row> it = inputSheet.rowIterator();

        while (it.hasNext()) {
            Row row = it.next();
            rows.put(row.getCell(colRef).getStringCellValue(), row);
        }

        fis.close();
    }

    public void writeExcel() throws IOException {
        FileOutputStream fos = new FileOutputStream(excelFile);

        workbook.write(fos);

        fos.flush();
        fos.close();

        workbook.close();
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

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

    public int getColRef() {
        return colRef;
    }

    public void setColRef(String colRef) {
        this.colRef = getIntCol(colRef);
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(String colSize) {
        this.colSize = getIntCol(colSize);
    }

    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(String colWidth) {
        this.colWidth = getIntCol(colWidth);
    }

    public int getColHeight() {
        return colHeight;
    }

    public void setColHeight(String colHeight) {
        this.colHeight = getIntCol(colHeight);
    }

    private int getIntCol(String s) {
        int pow = 1, res = 0;
        s = s.toUpperCase();
        for (int i = s.length() - 1; i >= 0; i --) {
            res = res * pow + (s.charAt(i) - 'A' + 1);
            pow *= 26;
        }
        return res - 1;
    }
}