package main.adapter;

import main.model.Eintrag;

import javax.swing.*;

public class AusgabenDetailansichtAdapter {

    public static String[] getEigenschaften(Eintrag eintrag){
        String eigenschaften[] = new String[8];

        eigenschaften[0] = Eintrag.getAlleAttributnamen()[0] + ": " + eintrag.getBezeichnung();
        eigenschaften[1] = Eintrag.getAlleAttributnamen()[1] + ": " + eintrag.getBeschreibung();
        eigenschaften[2] = Eintrag.getAlleAttributnamen()[2] + ": " + eintrag.getBetrag() + "€";
        eigenschaften[3] = Eintrag.getAlleAttributnamen()[3] + ": " + eintrag.getArt();
        eigenschaften[4] = Eintrag.getAlleAttributnamen()[4] + ": " + eintrag.getKategorie().getBezeichnung();
        eigenschaften[5] = Eintrag.getAlleAttributnamen()[5] + ": " + eintrag.getDatum();
        eigenschaften[6] = Eintrag.getAlleAttributnamen()[6] + ": " + eintrag.getProduktliste();
        eigenschaften[7] = Eintrag.getAlleAttributnamen()[7] + ": " + eintrag.getSystemaenderung().getZeitstempel();

        return eigenschaften;
    }
}
