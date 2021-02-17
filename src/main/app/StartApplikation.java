package main.app;

import main.gui.Eintraege.EintraegeAnzeigenGUI;
import main.gui.Eintraege.EintraegeDetailansichtGUI;
import main.gui.Eintraege.EingebenGUI;
import main.gui.BenutzerAnlegenGUI;
import main.gui.GUIController;
import main.gui.UebersichtsGUI;
import main.model.Benutzer;
import main.model.EMail;
import main.model.Eintrag;
import main.util.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartApplikation {

    // GUI Components
    private static UebersichtsGUI uebersichtsGUI;
    private static BenutzerAnlegenGUI benutzerAnlegenGUI;
    private static EintraegeAnzeigenGUI eintraegeAnzeigenGUI;
    private static EintraegeDetailansichtGUI eintraegeDetailansichtGUI;

    // CSV Reader und Files
    private static CSVReader csvReaderBenutzer;

    // Benutzer
    private static Benutzer benutzer;

    private static String benutzerFile = "./resources/benutzer.csv";
    private static String ausgabenFile;
    private static String einnahmenFile;

    public static void main( String[] args ) throws Exception {
        if(neuerNutzer() == true){
            benutzerAnlegenGUI = new BenutzerAnlegenGUI();
            new GUIController(benutzerAnlegenGUI);
        } else {
            buildUebersichtsGUI();
            new GUIController(uebersichtsGUI);
        }
    }

    private static boolean neuerNutzer() {
        List<String[]> dateiInhalt = getBenutzerDateiinhalt();

        if(dateiInhalt.size() > 0) {
            benutzerAnlegen(dateiInhalt);
            return false;
        }
        return true;
    }

    private static List<String[]> getBenutzerDateiinhalt(){
        csvReaderBenutzer = new CSVReader(benutzerFile);
        // Liste für Inhalt der Raum-Datei
        List<String[]> dateiInhalt = new ArrayList<>();
        try {
            dateiInhalt = csvReaderBenutzer.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dateiInhalt;
    }

    private static void benutzerAnlegen(List<String[]> dateiInhalt) {
        benutzer = new Benutzer(dateiInhalt.get(0)[0], dateiInhalt.get(0)[1], emailAnlegen(dateiInhalt.get(0)[2]));
    }

    private static EMail emailAnlegen(String emailString){
        String[] emailSplit1 = emailString.split("@");
        String[] emailSplit2 = emailSplit1[1].split("\\.");
        return new EMail(emailSplit1[0], emailSplit2[0], emailSplit2[1]);
    }

    public static void buildUebersichtsGUI() {
        benutzerAnlegen(getBenutzerDateiinhalt());
        try{
            uebersichtsGUI = new UebersichtsGUI(benutzer);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static UebersichtsGUI buildUebersichtsGUIfromController() {
        buildUebersichtsGUI();
        return uebersichtsGUI;
    }

    public static EintraegeAnzeigenGUI buildEintraegeAnzeigenGUI(){
        eintraegeAnzeigenGUI = new EintraegeAnzeigenGUI(benutzer);
        return eintraegeAnzeigenGUI;
    }

    public static EintraegeDetailansichtGUI buildEintraegeDetailansichtGUI(Eintrag eintrag){
        eintraegeDetailansichtGUI = new EintraegeDetailansichtGUI(eintrag);
        return eintraegeDetailansichtGUI;
    }

    public static EingebenGUI buildEingebenGUI() {
        return new EingebenGUI();
    }

    public static String buildAusgabenFileName() {
        ausgabenFile = "resources/ausgaben" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
        return ausgabenFile;
    }

    public static String buildEinnahmenFileName() {
        einnahmenFile = "resources/einnahmen" + benutzer.getVorname() + benutzer.getNachname() + ".csv";
        return einnahmenFile;
    }
}