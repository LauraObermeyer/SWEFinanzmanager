package main.model;

import java.util.Date;

public class Systemaenderung {
    private Date zeitstempel;

    public Systemaenderung() {
        zeitstempel = new Date();
    }

    public Systemaenderung(Date zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    public Date getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(Date zeitstempel) {
        this.zeitstempel = zeitstempel;
    }
}
