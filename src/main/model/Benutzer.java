package main.model;

import java.util.UUID;

public class Benutzer {
    private UUID id;
    private String vorname;
    private String nachname;
    private EMail email;

    // Konstruktor zum Erstellen des Benutzers aus der csv-Datei
    public Benutzer(String vorname, String nachname, EMail email) {
        this.id = UUID.randomUUID();
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public Benutzer(UUID id, String vorname, String nachname, EMail email) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public EMail getEmail() {
        return email;
    }

    public void setEmail(EMail email) {
        this.email = email;
    }

    public UUID getId(){ return this.id; };

    public String getTitle() {
        return "SWE-Finanzverwalter von " + this.getVorname() + " " + this.getNachname();
    }

}
