package main.gui;

public class GUIController {

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
}
