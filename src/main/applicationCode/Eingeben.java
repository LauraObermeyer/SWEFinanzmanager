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

    private String[] neueZeile;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

    // Header für Datei
    private final String[] header = Eintrag.getAlleAttributnamenFürFile();

    private EintragRepository eintragVerwaltung;
    public Eingeben(EintragRepository eintragVerwaltung) {
        this.eintragVerwaltung = eintragVerwaltung;
        this.neueZeile = new String[8];
    }

    public void anlegen(String[] textfelderInhalt, boolean neuAnlegen, Eintrag eintrag) {

        if(!neuAnlegen){
            EintraegeDetailansicht eintraegeDetailansicht = new EintraegeDetailansicht(this.eintragVerwaltung);
            eintraegeDetailansicht.deleteEintrag(eintrag);
            eintragEigenschaftenUpdaten(textfelderInhalt, eintrag);
            this.zeilenInhaltAufbauen(textfelderInhalt, neuAnlegen, eintrag);
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
                Eintrag neuerEintrag = new Eintrag(id, bezeichnung, beschreibung, betrag, art, kategorie, datum, produktliste);
                this.eintragVerwaltung.fuegeHinzu(neuerEintrag);
                this.zeilenInhaltAufbauen(textfelderInhalt, neuAnlegen, neuerEintrag);
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

        // Bisherigen Inhalt aus der csv Datei auslesen
        dateiInhalt = new ArrayList<>();
        try {
            dateiInhalt = csvReader.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Neuen Eintrag der csv Datei hinzufügen
        dateiInhalt.add(this.neueZeile);
        try {
            csvWriter.writeDataToFile(dateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zeilenInhaltAufbauen(String[] textfelderInhalt, boolean neuAnlegen, Eintrag eintrag) {
        if(neuAnlegen){
            this.neueZeile[0] = eintrag.getId().toString();
            this.neueZeile[7] = eintrag.getSystemaenderung().getZeitstempel().toString();
        }else {
            this.neueZeile[0] = eintrag.getId().toString();
            this.neueZeile[7] = eintrag.getSystemaenderung().getZeitstempel().toString();
        }
        this.neueZeile[1] = textfelderInhalt[0];
        this.neueZeile[2] = textfelderInhalt[1];
        this.neueZeile[3] = textfelderInhalt[3];
        this.neueZeile[4] = textfelderInhalt[4];
        this.neueZeile[5] = textfelderInhalt[5];
        this.neueZeile[6] = textfelderInhalt[6];
    }

    public void eintragEigenschaftenUpdaten(String[] textfelderInhalt, Eintrag eintrag){
        try{
            eintrag.setBezeichnung(textfelderInhalt[0]);
            eintrag.setBeschreibung(textfelderInhalt[1]);
            if (textfelderInhalt[2] == "Ausgabe") {
                eintrag.setArt(Art.Ausgabe);
            } else {
                eintrag.setArt(Art.Einnahme);
            }
            eintrag.setBetrag(Double.parseDouble(textfelderInhalt[3]));
            eintrag.setKategorie(new Kategorie(textfelderInhalt[4]));
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
            eintrag.setDatum(formatter.parse(textfelderInhalt[5]));
            eintrag.setProduktliste(textfelderInhalt[6]);
            eintrag.setSystemaenderung(new Systemaenderung());

            this.eintragVerwaltung.fuegeHinzu(eintrag);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
