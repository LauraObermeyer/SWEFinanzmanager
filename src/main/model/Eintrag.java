package main.model;

import java.util.Date;

public class Eintrag {
    private String bezeichnung;
    private String beschreibung;
    private double betrag;
    private Art art;
    private Kategorie kategorie;
    private Date datum;
    private String produktliste; //TODO: Produkt als eigene Klasse modellieren?
    private Systemaenderung systemaenderung;

    public Eintrag(String bezeichnung, String beschreibung, double betrag, Art art, Kategorie kategorie, Date datum, String produktliste) {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.art = art;
        this.kategorie = kategorie;
        this.datum = datum;
        this.produktliste = produktliste;
        this.systemaenderung = new Systemaenderung(); // systemaenderung wird bei Erstellen des Eintrags automatisch mit aktuellem Zeitstempel erstellt
    }

    // Konstruktor zum Auslesen der csv Dateien. Die Systemaenderung wird übergeben
    public Eintrag(String bezeichnung, String beschreibung, double betrag, Art art, Kategorie kategorie, Date datum, String produktliste, Systemaenderung systemaenderung) {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.art = art;
        this.kategorie = kategorie;
        this.datum = datum;
        this.produktliste = produktliste;
        this.systemaenderung = systemaenderung;
    }

    public static final String[] getAlleAttributnamen() {
        String alleAttributnamen[] = {"Bezeichnung", "Beschreibung", "Betrag", "Art", "Kategorie", "Datum", "Produktliste", "Systemaenderung"};
        return alleAttributnamen;
    }

    public static final String[] getAlleAttributnamenFürFile() {
        String alleAttributnamen[] = {"Bezeichnung", "Beschreibung", "Betrag", "Kategorie", "Datum", "Produktliste", "Systemaenderung"};
        return alleAttributnamen;
    }

    public static final String[] getAlleAttributnamenFürTabelle() {
        String alleAttributnamen[] = {"Bezeichnung", "Betrag in €", "Art", "Kategorie", "Datum", "Produktliste", "..."};
        return alleAttributnamen;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getProduktliste() {
        return produktliste;
    }

    public void setProduktliste(String produktliste) {
        this.produktliste = produktliste;
    }

    public Systemaenderung getSystemaenderung() {
        return systemaenderung;
    }

    public void setSystemaenderung(Systemaenderung systemaenderung) {
        this.systemaenderung = systemaenderung;
    }
}