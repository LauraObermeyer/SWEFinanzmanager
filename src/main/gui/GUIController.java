package main.gui;

import main.app.StartApplikation;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.gui.Ausgaben.AusgabenDetailansichtGUI;

public class GUIController implements IGUIEventListener {

    // GUI Components
    private UebersichtsGUI uebersichtsGUI;
    private BenutzerAnlegenGUI benutzerAnlegenGUI;
    private AusgabenDetailansichtGUI ausgabenDetailansichtGUI;

    public GUIController(BenutzerAnlegenGUI benutzerAnlegenGUI) throws Exception {
        this.benutzerAnlegenGUI = benutzerAnlegenGUI;
        benutzerAnlegenGUI.addListener(this);
    }

    public GUIController(UebersichtsGUI uebersichtsGUI) throws Exception {
        this.uebersichtsGUI = uebersichtsGUI;
        uebersichtsGUI.addListener(this);
    }

    public void eventFired(GUIEvent guiEvent) {
        switch (guiEvent.getMessage()){
            case "Abbrechen":
                break;
            case "Speichern":
                buildUebersichtsGUI();
                break;
            case "DetailansichtOeffnen":
                buildDetailansichtGUI();
                break;
            case "ZurueckZuUebersicht":
                refreshUebersichtsGUI();
                break;
        }
    }

    private void buildDetailansichtGUI() {
        uebersichtsGUI.buildDetailansichtGUI();
        uebersichtsGUI.getAusgabenDetailansichtGUI().addListener(this);
    }

    private void buildUebersichtsGUI() {
        uebersichtsGUI = StartApplikation.buildUebersichtsGUIfromController();
        uebersichtsGUI.addListener(this);
    }

    private void refreshUebersichtsGUI() {
        uebersichtsGUI.refreshUebersichtsGUI();
        uebersichtsGUI.addListener(this);
    }

    public UebersichtsGUI getUebersichtsGUI() {
        return uebersichtsGUI;
    }

    public void setUebersichtsGUI(UebersichtsGUI uebersichtsGUI) {
        this.uebersichtsGUI = uebersichtsGUI;
        uebersichtsGUI.addListener(this);
    }

    public BenutzerAnlegenGUI getBenutzerAnlegenGUI() {
        return benutzerAnlegenGUI;
    }

    public void setBenutzerAnlegenGUI(BenutzerAnlegenGUI benutzerAnlegenGUI) {
        this.benutzerAnlegenGUI = benutzerAnlegenGUI;
        benutzerAnlegenGUI.addListener(this);
    }
}
