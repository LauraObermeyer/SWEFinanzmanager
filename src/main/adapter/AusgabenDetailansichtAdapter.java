package main.adapter;

import main.model.Eintrag;

import javax.swing.*;

public class AusgabenDetailansichtAdapter {

    public static String[] getEigenschaften(Eintrag eintrag){
        String eigenschaften[] = new String[7];

        eigenschaften[0] = Eintrag.getAlleAttributnamen()[0] + ": " + eintrag.getBezeichnung();
        eigenschaften[1] = Eintrag.getAlleAttributnamen()[1] + ": " + eintrag.getBeschreibung();
        eigenschaften[2] = Eintrag.getAlleAttributnamen()[2] + ": " + eintrag.getArt();
        eigenschaften[3] = Eintrag.getAlleAttributnamen()[3] + ": " + eintrag.getKategorie().getBezeichnung();
        eigenschaften[4] = Eintrag.getAlleAttributnamen()[4] + ": " + eintrag.getDatum();
        eigenschaften[5] = Eintrag.getAlleAttributnamen()[5] + ": " + eintrag.getProduktliste();
        eigenschaften[6] = Eintrag.getAlleAttributnamen()[6] + ": " + eintrag.getSystemaenderung().getZeitstempel();

        return eigenschaften;
    }
}
