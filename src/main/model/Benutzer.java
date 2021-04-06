package main.model;

public class Benutzer {
    private String vorname;
    private String nachname;
    private EMail email;

    // Konstruktor zum Erstellen des Benutzers aus der csv-Datei
    public Benutzer(String vorname, String nachname, EMail email) {
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

    public String getId(){ return this.vorname + " " + this.nachname };

    public String getTitle() {
        return "SWE-Finanzverwalter von " + this.getVorname() + " " + this.getNachname();
    }

}
