package main.model;

import java.util.Date;
import java.util.UUID;

public class EintragErbauer {

    private UUID id;
    private String bezeichnung;
    private String beschreibung;
    private double betrag;
    private Art art;
    private Kategorie kategorie;
    private Date datum;
    private String produktliste; //TODO: Produkt als eigene Klasse modellieren?
    private Systemaenderung systemaenderung;

    // Erbauer, der zu Testzwecken verwendet werden kann und einen default Eintrag zurückliefert
    public EintragErbauer() {
        super();
        this.id = UUID.randomUUID();
        this.bezeichnung = "Testausgabe";
        this.beschreibung = "Ich bin eine Testbeschreibung";
        this.betrag = 59.99;
        this.art = Art.Ausgabe;
        this.kategorie = new Kategorie("Lebensmittel", "Diese Kategorie dient der Lebensmittelausgaben.");
        //this.datum = Calendar.set(2021, 2, 16);
        this.produktliste = "Kartoffeln, Milch, Shampoo, Toilettenpapier";
        this.systemaenderung = new Systemaenderung();
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
