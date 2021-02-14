package main.adapter;

import main.app.StartApplikation;
import main.gui.Ausgaben.AusgabenAnzeigenGUI;
import main.gui.Ausgaben.AusgabenDetailansichtGUI;

public class UebersichtsAdapter {

    /*TODO: Bis jetzt ist dieser Adapter etwas unnötig.
        Müssen wir uns noch überlegen, ob das so wirklich sinnvoll ist,
        weil er ja einfach nur die Methodenaufrufe von der UebersichtsGUI an die StartApplikation weiterleitet.
        Sind Methodenaufrufe überhaupt Logik, also Adapter Sache?
     */

    public static AusgabenDetailansichtGUI buildDetailansichtGUI(AusgabenAnzeigenGUI ausgabenAnzeigenGUI){
        return StartApplikation.buildAusgabenDetailansichtGUI(ausgabenAnzeigenGUI.getClickedEintrag());
    }

    public static AusgabenAnzeigenGUI refreshUebersichtsGUI(){
        return StartApplikation.buildAusgabenAnzeigenGUI();
    }
}
