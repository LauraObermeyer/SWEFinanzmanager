package main.adapter;

import main.model.Eintrag;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class EintraegeDetailansichtAdapter {

    public String[] getEigenschaften(Eintrag eintrag){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        String datum = formatter.format(eintrag.getDatum());

        //TODO: Wie wollen wir den Zeitstempel darstellen, in welchem Format?
        //String zeitstempel = formatter.format(eintrag.getSystemaenderung().getZeitstempel());

        String eigenschaften[] = new String[9];

        eigenschaften[0] = Eintrag.getAlleAttributnamen()[0] + ": " + eintrag.getId();
        eigenschaften[1] = Eintrag.getAlleAttributnamen()[1] + ": " + eintrag.getBezeichnung();
        eigenschaften[2] = Eintrag.getAlleAttributnamen()[2] + ": " + eintrag.getBeschreibung();
        eigenschaften[3] = Eintrag.getAlleAttributnamen()[3] + ": " + eintrag.getBetrag() + "€";
        eigenschaften[4] = Eintrag.getAlleAttributnamen()[4] + ": " + eintrag.getArt();
        eigenschaften[5] = Eintrag.getAlleAttributnamen()[5] + ": " + eintrag.getKategorie().getBezeichnung();
        eigenschaften[6] = Eintrag.getAlleAttributnamen()[6] + ": " + datum;
        eigenschaften[7] = Eintrag.getAlleAttributnamen()[7] + ": " + eintrag.getProduktliste();
        eigenschaften[8] = Eintrag.getAlleAttributnamen()[8] + ": " + eintrag.getSystemaenderung().getZeitstempel();
        //eigenschaften[7] = Eintrag.getAlleAttributnamen()[7] + ": " + zeitstempel;

        return eigenschaften;
    }
}
