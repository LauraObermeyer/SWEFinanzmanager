package main.applicationCode;

import main.app.StartApplikation;
import main.model.*;
import main.util.CSVReader;
import main.util.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Eingeben {
    //CSVReader und Writer
    private CSVReader csvReader;
    private CSVWriter csvWriter;

    private String ausgabenFile;
    private String einnahmenFile;

    private List<String[]> dateiInhalt;

    // Header für Datei
    private final String[] header = Eintrag.getAlleAttributnamenFürFile();

    private EintragRepository eintragVerwaltung;
    public Eingeben(EintragRepository eintragVerwaltung) {
        this.eintragVerwaltung = eintragVerwaltung;
    }

    public void anlegen(String[] textfelderInhalt, boolean neuAnlegen, Eintrag eintrag) {
        if(!neuAnlegen){
            EintraegeDetailansicht eintraegeDetailansicht = new EintraegeDetailansicht(this.eintragVerwaltung);
            eintraegeDetailansicht.deleteEintrag(eintrag);
        }
        ausgabenFile = StartApplikation.ausgabenFileNameErstellen();
        einnahmenFile = StartApplikation.einnahmenFileNameErstellen();

        // Je nachdem, ob Ausgabe oder Einnahme die entsprechenden csvWriter und Reader erstellen
        if(textfelderInhalt[2] == "Ausgabe") {
            csvWriter = new CSVWriter(ausgabenFile, true);
            csvReader = new CSVReader(ausgabenFile);
        } else {
            csvWriter = new CSVWriter(einnahmenFile, true);
            csvReader = new CSVReader(einnahmenFile);
        }

        // Neue Zeile für Datei bauen
        String[] neueZeile = new String[7];
        neueZeile[0] = textfelderInhalt[0];
        neueZeile[1] = textfelderInhalt[1];
        neueZeile[2] = textfelderInhalt[3];
        neueZeile[3] = textfelderInhalt[4];
        neueZeile[4] = textfelderInhalt[5];
        neueZeile[5] = textfelderInhalt[6];
        neueZeile[6] = new Systemaenderung().getZeitstempel().toString();

        // Bisherigen Inhalt aus der csv Datei auslesen
        dateiInhalt = new ArrayList<>();
        try {
            dateiInhalt = csvReader.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Neuen Eintrag der csv Datei hinzufügen
        dateiInhalt.add(neueZeile);
        try {
            csvWriter.writeDataToFile(dateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
