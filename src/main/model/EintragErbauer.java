package main.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class EintragErbauer {

    private UUID id;
    private String bezeichnung;
    private String beschreibung;
    private double betrag;
    private Art art;
    private Kategorie kategorie;
    private Date datum;
    private String produktliste;
    private Systemaenderung systemaenderung;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

    // Erbauer, der einen default Eintrag zurückliefert
    public EintragErbauer() {
        super();
        this.id = UUID.randomUUID();
        this.bezeichnung = "Testausgabe";
        this.beschreibung = "Ich bin eine Testbeschreibung";
        this.betrag = 59.99;
        this.art = Art.Ausgabe;
        this.kategorie = new Kategorie("Lebensmittel", "Diese Kategorie dient der Lebensmittelausgaben.");
        this.produktliste = "Kartoffeln, Milch, Shampoo, Toilettenpapier";
        this.systemaenderung = new Systemaenderung();
        try {
            this.datum = formatter.parse("13.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
            this.datum = new Date();
        }
    }

    public EintragErbauer mitAllenEigenschaften(String bezeichnung, String beschreibung, double betrag, Art art, Kategorie kategorie, String datum, String produktliste){
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.art = art;
        this.kategorie = kategorie;
        try {
            this.datum = formatter.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
            this.datum = new Date();
        }
        this.produktliste = produktliste;
        return this;
    }

    public EintragErbauer mitBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        return this;
    }

    public EintragErbauer mitBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public EintragErbauer mitBetrag(double betrag) {
        this.betrag = betrag;
        return this;
    }

    public EintragErbauer mitArt(Art art) {
        this.art = art;
        return this;
    }

    public Eintrag build() {
        return new Eintrag(
                this.id,
                this.bezeichnung,
                this.beschreibung,
                this.betrag,
                this.art,
                this.kategorie,
                this.datum,
                this.produktliste,
                this.systemaenderung);
    }
}
