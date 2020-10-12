package main.app;

import main.gui.GUIController;

import java.io.File;

public class StartApplikation {

    private static String filePath;
    private static String benutzerFile = "./resources/benutzer.csv";

    public static void main( String[] args ) throws Exception {
        new GUIController();
    }

    public static String getBenutzerFile() {
        return benutzerFile;
    }
}