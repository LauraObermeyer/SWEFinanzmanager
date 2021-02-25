package main.adapter;

import main.model.Eintrag;
import main.util.CSVReader;
import main.util.CSVWriter;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EintraegeDetailansichtAdapter {

    public static String[] getEigenschaften(Eintrag eintrag){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        String datum = formatter.format(eintrag.getDatum());

        //TODO: Wie wollen wir den Zeitstempel darstellen, in welchem Format?
        //String zeitstempel = formatter.format(eintrag.getSystemaenderung().getZeitstempel());

        String eigenschaften[] = new String[8];

        eigenschaften[0] = Eintrag.getAlleAttributnamen()[0] + ": " + eintrag.getBezeichnung();
        eigenschaften[1] = Eintrag.getAlleAttributnamen()[1] + ": " + eintrag.getBeschreibung();
        eigenschaften[2] = Eintrag.getAlleAttributnamen()[2] + ": " + eintrag.getBetrag() + "€";
        eigenschaften[3] = Eintrag.getAlleAttributnamen()[3] + ": " + eintrag.getArt();
        eigenschaften[4] = Eintrag.getAlleAttributnamen()[4] + ": " + eintrag.getKategorie().getBezeichnung();
        eigenschaften[5] = Eintrag.getAlleAttributnamen()[5] + ": " + datum;
        eigenschaften[6] = Eintrag.getAlleAttributnamen()[6] + ": " + eintrag.getProduktliste();
        eigenschaften[7] = Eintrag.getAlleAttributnamen()[7] + ": " + eintrag.getSystemaenderung().getZeitstempel();
        //eigenschaften[7] = Eintrag.getAlleAttributnamen()[7] + ": " + zeitstempel;

        return eigenschaften;
    }
}
