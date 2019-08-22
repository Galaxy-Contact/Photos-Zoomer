package controller;

import model.DataModel;
import org.opencv.core.Core;
import view.GUI;

import javax.swing.*;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static DataModel model = new DataModel();

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(() -> new GUI(model));
    }

}
