package main.gui;

import main.event.GUIEvent;
import main.event.IGUIEventListener;

import java.io.File;

public class GUIController implements IGUIEventListener {

    // GUI Components
    private UebersichtsGUI uebersichtsGUI;
    private BenutzerAnlegenGUI benutzerAnlegenGUI;

    public GUIController() throws Exception {
        if(neuerNutzer() == true){
            benutzerAnlegenGUI = new BenutzerAnlegenGUI();
        } else {
            uebersichtsGUI = new UebersichtsGUI();
        }
    }

    private boolean neuerNutzer() {
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
