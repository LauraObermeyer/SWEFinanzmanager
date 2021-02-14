package main.adapter;

import main.model.Art;
import main.model.Benutzer;
import main.model.Eintrag;
import main.model.Kategorie;
import main.model.Systemaenderung;
import main.util.CSVReader;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EintraegeAnzeigenAdapter {

    private static CSVReader csvReaderAusgaben;
    private static CSVReader csvReaderEinnahmen;
    private static String ausgabenFile;
    private static String einnahmenFile;

    // Liste aller Einträge (die werden in der Tabelle angezeigt)
    private static List<Eintrag> eintraege;

    public static void buildFileName(Benutzer benutzer) {
        ausgabenFile = "resources/ausgaben" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
        einnahmenFile = "resources/einnahmen" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
    }

    public static String[][] getEintragListe(){
        // csvReader initialisieren
        csvReaderAusgaben = new CSVReader(ausgabenFile);
        csvReaderEinnahmen = new CSVReader(einnahmenFile);
        // Liste für Inhalt der Ausgaben-Datei
        List<String[]> dateiInhaltAusgaben = new ArrayList<>();
        List<String[]> dateiInhaltEinnahmen = new ArrayList<>();

        // Einträge aus entsprechenden csv-Datei auslesen
        try {
            dateiInhaltAusgaben = csvReaderAusgaben.readData();
            dateiInhaltEinnahmen = csvReaderEinnahmen.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        eintraege = new ArrayList<>();

        // Basierend auf Text aus Ausgaben-Datei die entsprechenden Einträge generieren (zeilenweise) und zur Liste "eintraege" hinzufügen
        for (int i = 0; i < dateiInhaltAusgaben.size(); i++) {
            if(dateiInhaltAusgaben.get(i).length == 7) {
                try {
                    String[] zeile = dateiInhaltAusgaben.get(i);
                    String bezeichnung = zeile[0];
                    String beschreibung = zeile[1];
                    Double betrag = Double.parseDouble(zeile[2]);
                    Kategorie kategorie = new Kategorie(zeile[3]);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                    Date datum = formatter.parse(zeile[4]);
                    String produktliste = zeile[5];
                    eintraege.add(new Eintrag(bezeichnung, beschreibung, betrag, Art.valueOf("Ausgabe"), kategorie, datum, produktliste));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Basierend auf Text aus Einnahmen-Datei die entsprechenden Einträge generieren (zeilenweise) und zur Liste "eintraege" hinzufügen
        for (int i = 0; i < dateiInhaltEinnahmen.size(); i++) {
            if(dateiInhaltEinnahmen.get(i).length == 7) {
                try {
                    String[] zeile = dateiInhaltEinnahmen.get(i);
                    String bezeichnung = zeile[0];
                    String beschreibung = zeile[1];
                    Double betrag = Double.parseDouble(zeile[2]);
                    Kategorie kategorie = new Kategorie(zeile[3]);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                    Date datum = formatter.parse(zeile[4]);
                    String produktliste = zeile[5];
                    //Systemaenderung system = new Systemaenderung(Timestamp.valueOf(zeile[6]));
                    eintraege.add(new Eintrag(bezeichnung, beschreibung, betrag, Art.valueOf("Einnahme"), kategorie, datum, produktliste/*, system*/));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Tabelleninhalt aufbauen
        String tabellenInhalt[][];
        if(eintraege.size() > 0) { // nur wenn Ausgaben vorhanden sind
            tabellenInhalt = new String[eintraege.size()][10];
        } else{
            tabellenInhalt = new String[0][10];
        }

        // Für jeden erstellten Eintrag die anzuzeigenden Attribute hinzufügen. Es werden alle Attribute, außer Beschreibung und Systemänderung angezeigt
        for(int j = 0; j < eintraege.size(); j++) {
            Eintrag eintrag = eintraege.get(j);
            tabellenInhalt[j][0] = eintrag.getBezeichnung();
            tabellenInhalt[j][1] = String.valueOf(eintrag.getBetrag());
            tabellenInhalt[j][2] = String.valueOf(eintrag.getArt());
            tabellenInhalt[j][3] = String.valueOf(eintrag.getKategorie().getBezeichnung());
            tabellenInhalt[j][4] = String.valueOf(eintrag.getDatum());
            tabellenInhalt[j][5] = String.valueOf(eintrag.getProduktliste());
        }

        return tabellenInhalt;
    }

    public static List<Eintrag> getEintraege() {
        return eintraege;
    }
}
