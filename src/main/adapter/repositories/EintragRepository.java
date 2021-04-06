package main.adapter.repositories;

import main.model.Eintrag;
import main.model.Kategorie;
import main.model.Art;

import java.util.HashSet;
import java.util.Optional;

public class EintragRepository implements main.model.EintragRepository {

    private HashSet<Eintrag> alleEintraege = new HashSet<Eintrag>();

    @Override
    public void fuegeHinzu(Eintrag eintrag) {
        this.alleEintraege.add(eintrag);
    }

    @Override
    public void entferne(Eintrag eintrag) {
        this.alleEintraege.remove(eintrag);

    }

    @Override
    public boolean pruefeObVorhanden(Eintrag eintrag) {
        return this.alleEintraege.contains(eintrag);
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
