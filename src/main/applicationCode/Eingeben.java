package main.applicationCode;

import main.app.StartApplikation;
import main.model.*;
import main.util.CSVReader;
import main.util.CSVWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Eingeben {
    //CSVReader und Writer
    private CSVReader csvReader;
    private CSVWriter csvWriter;

    private String ausgabenFile;
    private String einnahmenFile;

    private List<String[]> dateiInhalt;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

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
        }else{
            try {
                Art art;
                if (textfelderInhalt[2] == "Ausgabe") {
                    art = Art.Ausgabe;
                } else {
                    art = Art.Einnahme;
                }

                UUID id = UUID.randomUUID();
                String bezeichnung = textfelderInhalt[0];
                String beschreibung = textfelderInhalt[1];
                Double betrag = Double.parseDouble(textfelderInhalt[3]);
                Kategorie kategorie = new Kategorie(textfelderInhalt[4]);
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                Date datum = formatter.parse(textfelderInhalt[5]);
                String produktliste = textfelderInhalt[6];
                eintrag = new Eintrag(id, bezeichnung, beschreibung, betrag, art, kategorie, datum, produktliste);
                this.eintragVerwaltung.fuegeHinzu(eintrag);
            } catch(Exception e){
                e.printStackTrace();
            }
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
        String[] neueZeile = new String[8];
        neueZeile[0] = eintrag.getId().toString();
        neueZeile[1] = eintrag.getBezeichnung();
        neueZeile[2] = eintrag.getBeschreibung();
        neueZeile[3] = String.valueOf(eintrag.getBetrag());
        neueZeile[4] = String.valueOf(eintrag.getKategorie().getBezeichnung());
        neueZeile[5] = formatter.format(eintrag.getDatum());
        neueZeile[6] = String.valueOf(eintrag.getProduktliste());
        neueZeile[7] = eintrag.getSystemaenderung().toString();

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
