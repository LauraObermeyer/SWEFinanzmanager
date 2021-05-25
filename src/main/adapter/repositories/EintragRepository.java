package main.adapter.repositories;

import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

public class EintragRepository implements main.model.EintragRepository {

    private HashSet<Eintrag> alleEintraege = new HashSet<Eintrag>();

    @Override
    public void fuegeHinzu(Eintrag eintrag) {
        if(!this.pruefeObVorhanden(eintrag)){
            this.findeAlle().add(eintrag);
        }
    }

    @Override
    public void entferne(Eintrag eintrag) {
        for (Iterator<Eintrag> iter = this.findeAlle().iterator(); iter.hasNext();) {
            Eintrag vorhandenerEintrag = iter.next();
            if (vorhandenerEintrag.getId().compareTo(eintrag.getId())==0) {
                iter.remove();
            }
        }
    }

    @Override
    public boolean pruefeObVorhanden(Eintrag eintrag) {
        for (Eintrag vorhandenerEintrag : this.findeAlle()) {
            if(vorhandenerEintrag.getId().compareTo(eintrag.getId())==0){
                return true;
            }
        }
        return false;
    }

    @Override
    public HashSet<Eintrag> findeAlle() {
        return this.alleEintraege;
    }

    @Override
    public Optional<Eintrag> findeÜber(String bezeichnung) {
        for(Eintrag eintrag: this.findeAlle()) {
            if(eintrag.getBezeichnung() == bezeichnung) {
                return Optional.of(eintrag);
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Eintrag> findeAlleAus(Kategorie kategorie) {
        HashSet<Eintrag> eintraegeNachKategorie = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.findeAlle()) {
            if(eintrag.getKategorie() == kategorie) {
                eintraegeNachKategorie.add(eintrag);
            }
        }
        return eintraegeNachKategorie;
    }

    @Override
    public Iterable<Eintrag> findeAlleNach(Art art) {
        HashSet<Eintrag> eintraegeNachArt = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.findeAlle()) {
            if(art == eintrag.getArt() ) {
                eintraegeNachArt.add(eintrag);
            }
        }
        return eintraegeNachArt;
    }

    @Override
    public Iterable<Eintrag> findeAlleMitBetragGroeßer(Double betrag) {
        HashSet<Eintrag> eintraegeNachBetrag = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.findeAlle()) {
            if(eintrag.getBetrag() > betrag) {
                eintraegeNachBetrag.add(eintrag);
            }
        }
        return eintraegeNachBetrag;
    }

    @Override
    public Iterable<Eintrag> findeAlleMitBetragKleiner(Double betrag) {
        HashSet<Eintrag> eintraegeNachBetrag = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.findeAlle()) {
            if(eintrag.getBetrag() < betrag) {
                eintraegeNachBetrag.add(eintrag);
            }
        }
        return eintraegeNachBetrag;
    }

    @Override
    public int liefereAnzahlEintraege() {
        return this.findeAlle().size();
    }
}
