package main.adapter;

import main.model.Art;
import main.model.Benutzer;
import main.model.Eintrag;
import main.model.Kategorie;
import main.util.CSVReader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EintraegeAnzeigenAdapter {

    private static String ausgabenDateiName, einnahmenDateiName;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
    public static List<Eintrag> eintraege  = new ArrayList<>();

    public static void dateiNamenErstellenVon(Benutzer benutzer) {
        ausgabenDateiName = "resources/ausgaben" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
        einnahmenDateiName = "resources/einnahmen" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
    }

    public static String[][] getTabelleninhalt(){
        eintraegeGenerierenBasierendAuf(auslesenAusCsvDatei("Ausgaben"));
        eintraegeGenerierenBasierendAuf(auslesenAusCsvDatei("Einnahmen"));
        return tabelleninhaltAufbauen();
    }

    private static List<String[]> auslesenAusCsvDatei(String art) {
        CSVReader csvReader = (art == "Ausgaben") ? new CSVReader(ausgabenDateiName) : new CSVReader(einnahmenDateiName);
        List<String[]> dateiInhalt = new ArrayList<>();
        try {
            dateiInhalt = csvReader.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dateiInhalt;
    }

    private static void eintraegeGenerierenBasierendAuf(List<String[]> dateiInhalt) {
        for (int i = 0; i < dateiInhalt.size(); i++) {
            if(dateiInhalt.get(i).length == 7) {
                try {
                    String[] zeile = dateiInhalt.get(i);
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
    }

    private static String[][] tabelleninhaltAufbauen() {
        String tabelleninhalt[][];
        if(eintraege.size() > 0) {
            tabelleninhalt = new String[eintraege.size()][10];
        } else{
            tabelleninhalt = new String[0][10];
        }
        tabelleninhalt = tabelleninhaltZeilenweiseFüllen(tabelleninhalt);
        return tabelleninhalt;
    }

    /**
     * Für jeden erstellten Eintrag die in der Tabelle anzuzeigenden Attribute zum Tabelleninhalt hinzufügen.
     * Es sollen alle Attribute, außer Beschreibung und Systemänderung angezeigt werden.
     * In der letzten Spalte steht in jeder Zeile das Zeichen ">",
     * um anzudeuten, dass man die Spalte anklicken kann, um zur jeweiligen Detailansicht zu gelangen.
     * @return Tabelleninhalt
     */
    public static String[][] tabelleninhaltZeilenweiseFüllen(String[][] tabellenInhalt) {
        for(int j = 0; j < eintraege.size(); j++) {
            Eintrag eintrag = eintraege.get(j);
            tabellenInhalt[j][0] = eintrag.getBezeichnung();
            tabellenInhalt[j][1] = String.valueOf(eintrag.getBetrag());
            tabellenInhalt[j][2] = String.valueOf(eintrag.getArt());
            tabellenInhalt[j][3] = String.valueOf(eintrag.getKategorie().getBezeichnung());
            tabellenInhalt[j][4] = formatter.format(eintrag.getDatum());
            tabellenInhalt[j][5] = String.valueOf(eintrag.getProduktliste());
            tabellenInhalt[j][6] = ">";
        }

        return tabellenInhalt;

    }

    public static List<Eintrag> getEintraege() {
        return eintraege;
    }
}