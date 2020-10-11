package main.gui;

import main.app.StartApplikation;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
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

    public GUIController() throws Exception {
        if(neuerNutzer() == true){
            benutzerAnlegenGUI = new BenutzerAnlegenGUI();
        } else {
            uebersichtsGUI = new UebersichtsGUI();
        }
    }

    private boolean neuerNutzer() {
        csvReaderBenutzer = new CSVReader(benutzerFile);
        // Liste für Inhalt der Raum-Datei
        List<String[]> dateiInhalt = new ArrayList<>();
        try {
            dateiInhalt = csvReaderBenutzer.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(dateiInhalt.size() > 0) {
            return false;
        }
        return true;
    }

    public void eventFired(GUIEvent guiEvent) {
        switch (guiEvent.getMessage()){
            case "Abbrechen":
                break;
            case "Speichern":
                buildUebersichtsGUI();
                break;
        }
    }

    private void buildUebersichtsGUI() {
        try{
            uebersichtsGUI = new UebersichtsGUI();
            uebersichtsGUI.addListener(this);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }
}
