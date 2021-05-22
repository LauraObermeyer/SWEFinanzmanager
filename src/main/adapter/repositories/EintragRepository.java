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
            this.alleEintraege.add(eintrag);
        }
    }

    @Override
    public void entferne(Eintrag eintrag) {
        for (Iterator<Eintrag> iter = this.alleEintraege.iterator(); iter.hasNext();) {
            Eintrag vorhandenerEintrag = iter.next();
            if (vorhandenerEintrag.getId().compareTo(eintrag.getId())==0) {
                iter.remove();
            }
        }
    }

    @Override
    public boolean pruefeObVorhanden(Eintrag eintrag) {
        for (Eintrag vorhandenerEintrag : this.alleEintraege) {
            if(vorhandenerEintrag.getId().compareTo(eintrag.getId())==0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<Eintrag> findeAlle() {
        return this.alleEintraege;
    }

    @Override
    public Optional<Eintrag> findeÜber(String bezeichnung) {
        for(Eintrag eintrag: this.alleEintraege) {
            if(eintrag.getBezeichnung() == bezeichnung) {
                return Optional.of(eintrag);
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Eintrag> findeAlleAus(Kategorie kategorie) {
        HashSet<Eintrag> eintraegeNachKategorie = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.alleEintraege) {
            if(eintrag.getKategorie() == kategorie) {
                eintraegeNachKategorie.add(eintrag);
            }
        }
        return eintraegeNachKategorie;
    }

    @Override
    public Iterable<Eintrag> findeAlleNach(Art art) {
        HashSet<Eintrag> eintraegeNachArt = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.alleEintraege) {
            if(art == eintrag.getArt() ) {
                eintraegeNachArt.add(eintrag);
            }
        }
        return eintraegeNachArt;
    }

    @Override
    public Iterable<Eintrag> findeAlleMitBetragGroeßer(Double betrag) {
        HashSet<Eintrag> eintraegeNachBetrag = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.alleEintraege) {
            if(betrag > eintrag.getBetrag() ) {
                eintraegeNachBetrag.add(eintrag);
            }
        }
        return eintraegeNachBetrag;
    }

    @Override
    public Iterable<Eintrag> findeAlleMitBetragKleiner(Double betrag) {
        HashSet<Eintrag> eintraegeNachBetrag = new HashSet<Eintrag>();
        for(Eintrag eintrag : this.alleEintraege) {
            if(betrag < eintrag.getBetrag()) {
                eintraegeNachBetrag.add(eintrag);
            }
        }
        return eintraegeNachBetrag;
    }

    @Override
    public int liefereAnzahlEintraege() {
        return this.alleEintraege.size();
    }
}
