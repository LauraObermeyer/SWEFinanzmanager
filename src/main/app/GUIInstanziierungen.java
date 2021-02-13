package main.app;

import main.gui.BenutzerAnlegenGUI;
import main.gui.GUIController;
import main.gui.UebersichtsGUI;
import main.model.Benutzer;
import main.model.EMail;
import main.util.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIInstanziierungen {

    // GUI Components
    private UebersichtsGUI uebersichtsGUI;
    private BenutzerAnlegenGUI benutzerAnlegenGUI;

    // CSV Reader und Files
    private CSVReader csvReaderBenutzer;

    // Benutzer
    private Benutzer benutzer;

    private String filePath;
    private String benutzerFile = "./resources/benutzer.csv";

    public void main( String[] args ) throws Exception {
        if(neuerNutzer() == true){
            benutzerAnlegenGUI = new BenutzerAnlegenGUI();
            new GUIController(benutzerAnlegenGUI);
        } else {
            buildUebersichtsGUI();
            new GUIController(uebersichtsGUI);
        }
    }

    public String getBenutzerFile() {
        return benutzerFile;
    }

    private boolean neuerNutzer() {
        List<String[]> dateiInhalt = getBenutzerDateiinhalt();

        if(dateiInhalt.size() > 0) {
            benutzerAnlegen(dateiInhalt);
            return false;
        }
        return true;
    }

    private List<String[]> getBenutzerDateiinhalt(){
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

    private void benutzerAnlegen(List<String[]> dateiInhalt) {
        benutzer = new Benutzer(dateiInhalt.get(0)[0], dateiInhalt.get(0)[1], emailAnlegen(dateiInhalt.get(0)[2]));
    }

    private EMail emailAnlegen(String emailString){
        String[] emailSplit1 = emailString.split("@");
        String[] emailSplit2 = emailSplit1[1].split("\\.");
        return new EMail(emailSplit1[0], emailSplit2[0], emailSplit2[1]);
    }

    public void buildUebersichtsGUI() {
        benutzerAnlegen(getBenutzerDateiinhalt());
        try{
            uebersichtsGUI = new UebersichtsGUI(benutzer);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public void buildUebersichtsGUIfromController() {
        buildUebersichtsGUI();
        //GUIController.setUebersichtsGUI(uebersichtsGUI);
        /*TODO: Achso, meine zweite Idee, mit einer weiteren Klasse GUIInstanziierungen funktioniert auch nicht wirklich
            Weil in der Klasse müssts ja auch static sein, es sei denn wir erzeugen ein Objekt der Klasse
            Aber das müssten wir dann überall mitgeben, was vllt auch nicht so schön ist*/
    }
}
