package controller;

import java.io.File;
import java.util.ArrayList;

public class FolderBrowser {
    /*
        Browse files in the folder, including sub-directories
        Build a files map
     */

    public void recursivelyBrowseFiles(File cur, ArrayList<File> filesList) {
        if (cur.isFile()) {
            filesList.add(cur);
        } else {
            File[] listFile = cur.listFiles();
            if (listFile == null)
                return;
            for (File f : listFile) {
                recursivelyBrowseFiles(f, filesList);
            }
        }
    }

}
