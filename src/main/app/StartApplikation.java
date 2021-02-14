package main.app;

import main.event.GUIEvent;
import main.gui.Ausgaben.AusgabenAnzeigenGUI;
import main.gui.Ausgaben.AusgabenDetailansichtGUI;
import main.gui.Ausgaben.EingebenGUI;
import main.gui.BenutzerAnlegenGUI;
import main.gui.GUIController;
import main.gui.UebersichtsGUI;
import main.model.Benutzer;
import main.model.EMail;
import main.model.Eintrag;
import main.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartApplikation {

    // GUI Components
    private static UebersichtsGUI uebersichtsGUI;
    private static BenutzerAnlegenGUI benutzerAnlegenGUI;
    private static AusgabenAnzeigenGUI ausgabenAnzeigenGUI;
    private static AusgabenDetailansichtGUI ausgabenDetailansichtGUI;

    // CSV Reader und Files
    private static CSVReader csvReaderBenutzer;

    // Benutzer
    private static Benutzer benutzer;

    private static String benutzerFile = "./resources/benutzer.csv";

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

    public static AusgabenAnzeigenGUI buildAusgabenAnzeigenGUI(){
        ausgabenAnzeigenGUI = new AusgabenAnzeigenGUI(benutzer);
        return ausgabenAnzeigenGUI;
    }

    public static AusgabenDetailansichtGUI buildAusgabenDetailansichtGUI(Eintrag eintrag){
        ausgabenDetailansichtGUI = new AusgabenDetailansichtGUI(eintrag);
        return ausgabenDetailansichtGUI;
    }

    public static EingebenGUI buildEingebenGUI() {
        return new EingebenGUI();
    }
}