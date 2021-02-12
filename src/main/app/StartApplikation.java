package main.app;

import main.event.GUIEvent;
import main.gui.BenutzerAnlegenGUI;
import main.gui.GUIController;
import main.gui.UebersichtsGUI;
import main.model.Benutzer;
import main.model.EMail;
import main.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartApplikation {

    // GUI Components
    private static UebersichtsGUI uebersichtsGUI;
    private static BenutzerAnlegenGUI benutzerAnlegenGUI;

    // CSV Reader und Files
    private static CSVReader csvReaderBenutzer;

    // Benutzer
    private static Benutzer benutzer;

    private static String filePath;
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

    public static String getBenutzerFile() {
        return benutzerFile;
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

    private static void buildUebersichtsGUI() {
        benutzerAnlegen(getBenutzerDateiinhalt());
        try{
            uebersichtsGUI = new UebersichtsGUI(benutzer);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void buildUebersichtsGUIfromController() {
        buildUebersichtsGUI();
        //GUIController.setUebersichtsGUI(uebersichtsGUI);
        /*TODO: Die Zeile hier drüber musste ich auskommentieren, weil setUebersichtsGUI nicht statisch ist und auch nicht statisch gemacht werden kann
            Weil das die main Methode ist muss hier gefühlt alles static sein (vielleicht kann man das aber auch ändern?)
            Im GUIController muss hier aber auf jeden Fall die neue Instanz der uebersichtsGUI geupdated (bzw. hinzugefügt) werden
            Die einzige Idee die ich dazu spontan habe, wäre doch eine weitere Klasse zu machen, die die GUIs instanziiert, welche nicht sofort am Anfang instanziiert werden
            Weil die müsste dann nicht static sein, weils nicht die Main ist*/
    }
}