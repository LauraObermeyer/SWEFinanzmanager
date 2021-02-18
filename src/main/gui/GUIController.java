package main.gui;

import main.adapter.EintraegeDetailansichtAdapter;
import main.app.StartApplikation;
import main.applicationCode.EintraegeDetailansichtUseCase;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.gui.Eintraege.EintraegeDetailansichtGUI;
import main.gui.Eintraege.EingebenGUI;

public class GUIController implements IGUIEventListener {

    // GUI Components
    private UebersichtsGUI uebersichtsGUI;
    private BenutzerAnlegenGUI benutzerAnlegenGUI;
    private EintraegeDetailansichtGUI eintraegeDetailansichtGUI;
    private EingebenGUI eingebenGUI;

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
            case "EintragNeuAnlegen":
                buildEingebenGUI();
                break;
            case "Anlegen":
                refreshUebersichtsGUI();
                break;
            case "Loeschen":
                EintraegeDetailansichtUseCase.deleteEintrag(uebersichtsGUI.getAusgabenDetailansichtGUI().getEintrag());
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

    private void buildEingebenGUI() {
        eingebenGUI = StartApplikation.buildEingebenGUI();
        eingebenGUI.addListener(this);
    }
}
