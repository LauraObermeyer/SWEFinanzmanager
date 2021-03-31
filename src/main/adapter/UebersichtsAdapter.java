package main.adapter;

import main.app.StartApplikation;
import main.gui.Eintraege.EintraegeAnzeigenGUI;
import main.gui.Eintraege.EintraegeDetailansichtGUI;

public class UebersichtsAdapter {

    /*TODO: Bis jetzt ist dieser Adapter etwas unnötig.
        Müssen wir uns noch überlegen, ob das so wirklich sinnvoll ist,
        weil er ja einfach nur die Methodenaufrufe von der UebersichtsGUI an die StartApplikation weiterleitet.
        Sind Methodenaufrufe überhaupt Logik, also Adapter Sache?
     */

    public EintraegeDetailansichtGUI buildDetailansichtGUI(EintraegeAnzeigenGUI eintraegeAnzeigenGUI){
        return StartApplikation.buildEintraegeDetailansichtGuiVon(eintraegeAnzeigenGUI.getClickedEintrag());
    }

    public EintraegeAnzeigenGUI refreshUebersichtsGUI(){
        return StartApplikation.buildEintraegeAnzeigenGUI();
    }
}
