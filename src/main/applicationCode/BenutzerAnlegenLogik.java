package main.applicationCode;

import main.util.CSVReader;
import main.util.CSVWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BenutzerAnlegenLogik {

    private static CSVReader csvReader;
    private static CSVWriter csvWriter;
    private static String benutzerFile = "resources/benutzer.csv";
    private static List<String[]> dateiInhalt;
    private static final String[] header = {"Vorname", "Nachname", "EMail"};

    public static void benutzerSpeichern(JTextField jtfVorname, JTextField jtfNachname, JTextField jtfEmail) {
        try {
            csvWriter = new CSVWriter(benutzerFile, true);
            dateiInhalt = Collections.singletonList(new String[]{jtfVorname.getText(), jtfNachname.getText(), jtfEmail.getText()});
            csvWriter.writeDataToFile(dateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
