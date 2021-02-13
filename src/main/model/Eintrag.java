package main.model;

import java.util.Date;

public class Eintrag {
    private String bezeichnung;
    private String beschreibung;
    private Art art;
    private Kategorie kategorie;
    private Date datum;
    private String produktliste; //TODO: Produkt als eigene Klasse modellieren?
    private Systemaenderung systemaenderung;

    public Eintrag(String bezeichnung, String beschreibung, Art art, Kategorie kategorie, Date datum, String produktliste) {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.art = art;
        this.kategorie = kategorie;
        this.datum = datum;
        this.produktliste = produktliste;
        this.systemaenderung = new Systemaenderung(); // systemaenderung wird bei Erstellen des Eintrags automatisch mit aktuellem Zeitstempel erstellt
    }

    public static final String[] getAlleAttributnamen() {
        String alleAttributnamen[] = {"Bezeichnung", "Beschreibung", "Art", "Kategorie", "Datum", "Produktliste", "Systemaenderung"};
        return alleAttributnamen;
    }

    public static final String[] getAlleAttributnamenFürTabelle() {
        String alleAttributnamen[] = {"Bezeichnung", "Beschreibung", "Art", "Kategorie", "Datum", "Produktliste", "Systemaenderung", "..."};
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
