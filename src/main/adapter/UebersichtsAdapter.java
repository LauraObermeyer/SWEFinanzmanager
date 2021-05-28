package main.adapter;

import main.app.StartApplikation;
import main.gui.Eintraege.EintraegeAnzeigenGUI;
import main.gui.Eintraege.EintraegeDetailansichtGUI;

public class UebersichtsAdapter {

    public EintraegeDetailansichtGUI buildDetailansichtGUI(EintraegeAnzeigenGUI eintraegeAnzeigenGUI){
        return StartApplikation.buildEintraegeDetailansichtGuiVon(eintraegeAnzeigenGUI.getClickedEintrag());
    }

    public EintraegeAnzeigenGUI refreshUebersichtsGUI(){
        return StartApplikation.buildEintraegeAnzeigenGUI();
    }
}
