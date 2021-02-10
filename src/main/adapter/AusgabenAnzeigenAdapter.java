package main.adapter;

import main.model.Art;
import main.model.Benutzer;
import main.model.Eintrag;
import main.model.Kategorie;
import main.util.CSVReader;
import main.util.CSVWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AusgabenAnzeigenAdapter {

    private static CSVWriter csvWriter;
    private static CSVReader csvReader;
    private static String ausgabenFile;

    // Liste aller Einträge (die werden in der Tabelle angezeigt)
    private static List<Eintrag> ausgaben;

    public static void buildFileName(Benutzer benutzer) {
        ausgabenFile = "resources/ausgaben" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
    }

    public static String[][] getAusgabenListe(){
        // csvWriter und csvReader initialisieren
        csvWriter = new CSVWriter(ausgabenFile, true); // falls File noch nicht existiert wird es hier erzeugt
        csvReader = new CSVReader(ausgabenFile);
        // Liste für Inhalt der Ausgaben-Datei
        List<String[]> dateiInhalt = new ArrayList<>();

        // Exponate aus entsprechender csv-Datei auslesen
        try {
            dateiInhalt = csvReader.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Basierend auf Text aus Ausgaben-Datei die entsprechenden Einträge generieren (zeilenweise) und zur Liste "ausgaben" hinzufügen
        ausgaben = new ArrayList<>();
        for (int i = 0; i < dateiInhalt.size(); i++) {
            // Tabelleneinträge mit zu wenig Attributen und Eingaben überspringen
            if (dateiInhalt.get(i).length == 7 && dateiInhalt.get(i)[2].equals("Ausgabe")) {
                try {
                    String[] z = dateiInhalt.get(i); // Inhalt der aktuellen Zeile z
                    Kategorie kategorie = new Kategorie(z[3]);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                    Date datum = formatter.parse(z[4]);
                    ausgaben.add(new Eintrag(z[0], z[1], Art.valueOf("Ausgabe"), kategorie, datum, z[5]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Tabelleninhalt aufbauen
        String data[][];
        if(ausgaben.size() > 0) { // nur wenn Ausgaben vorhanden sind
            data = new String[ausgaben.size()][10];
        } else{
            data = new String[0][10];
        }
        // Für jede Ausgabe aus "ausgaben" alle Attribute zu "data" hinzufügen
        for(int i = 0; i < dateiInhalt.size(); i++){
            //TODO: Wenn Eingaben hinzu kommen buggt das hier wahrscheinlich
            data[i] = dateiInhalt.get(i);
            // Letzte Tabellenzeile soll andeuten, dass man auf Tabellenzeile klicken kann, um zu entsprechender Detailansicht zu gelangen
            //data[i][5] = ">";
        }

        return data;
    }

    public static List<Eintrag> getAusgaben() {
        return ausgaben;
    }
}
