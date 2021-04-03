package model;

import java.util.Date;
import java.util.UUID;

public class EintragErbauer {

    private UUID id;
    private String bezeichnung;
    private String beschreibung;
    private double betrag;
    private main.model.Art art;
    private main.model.Kategorie kategorie;
    private Date datum;
    private String produktliste; //TODO: Produkt als eigene Klasse modellieren?
    private main.model.Systemaenderung systemaenderung;

    // Erbauer, der zu Testzwecken verwendet werden kann und einen default Eintrag zurückliefert
    public EintragErbauer() {
        super();
        this.id = UUID.randomUUID();
        this.bezeichnung = "Testausgabe";
        this.beschreibung = "Ich bin eine Testbeschreibung";
        this.betrag = 59.99;
        this.art = main.model.Art.Ausgabe;
        this.kategorie = new main.model.Kategorie("Lebensmittel", "Diese Kategorie dient der Lebensmittelausgaben.");
        //this.datum = Calendar.set(2021, 2, 16);
        this.produktliste = "Kartoffeln, Milch, Shampoo, Toilettenpapier";
        this.systemaenderung = new main.model.Systemaenderung();
    }

    public main.model.Eintrag build() {
        return new main.model.Eintrag(
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
