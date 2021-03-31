package main.applicationCode;

import main.util.CSVReader;
import main.util.CSVWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BenutzerAnlegen {

    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String benutzerFile = "resources/benutzer.csv";
    private List<String[]> dateiInhalt;
    private final String[] header = {"Vorname", "Nachname", "EMail"};

    public void benutzerSpeichern(JTextField jtfVorname, JTextField jtfNachname, JTextField jtfEmail) {
        try {
            csvWriter = new CSVWriter(benutzerFile, true);
            dateiInhalt = Collections.singletonList(new String[]{jtfVorname.getText(), jtfNachname.getText(), jtfEmail.getText()});
            csvWriter.writeDataToFile(dateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
