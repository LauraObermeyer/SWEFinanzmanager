package main.gui;

import main.app.StartApplikation;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.model.Benutzer;
import main.model.EMail;
import main.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIController implements IGUIEventListener {

    // GUI Components
    private UebersichtsGUI uebersichtsGUI;
    private BenutzerAnlegenGUI benutzerAnlegenGUI;

    // CSV Reader und Files
    private CSVReader csvReaderBenutzer;
    private String benutzerFile = StartApplikation.getBenutzerFile();

    // Benutzer
    private Benutzer benutzer;

    public GUIController() throws Exception {
        if(neuerNutzer() == true){
            benutzerAnlegenGUI = new BenutzerAnlegenGUI();
            benutzerAnlegenGUI.addListener(this);
        } else {
            buildUebersichtsGUI();
        }
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

    public void eventFired(GUIEvent guiEvent) {
        switch (guiEvent.getMessage()){
            case "Abbrechen":
                break;
            case "Speichern":
                buildUebersichtsGUI();
                break;
            case "DetailansichtOeffnen":
                System.out.println("Test");
                break;
        }
    }

    private void buildUebersichtsGUI() {
        benutzerAnlegen(getBenutzerDateiinhalt());
        try{
            uebersichtsGUI = new UebersichtsGUI(benutzer);
            uebersichtsGUI.addListener(this);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }
}
