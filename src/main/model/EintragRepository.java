package main.model;

import java.util.Optional;

public interface EintragRepository {
    void fuegeHinzu(Eintrag eintrag);
    void entferne(Eintrag eintrag);
    boolean pruefeObVorhanden(Eintrag eintrag);
    Iterable<Eintrag>findeAlle();
    Optional<Eintrag> findeÜber(String bezeichnung);
    Iterable<Eintrag>findeAlleAus(Kategorie kateogrie);
    Iterable<Eintrag>findeAlleNach(Art art);
    Iterable<Eintrag>findeAlleMitBetragGroeßer(Double betrag);
    Iterable<Eintrag>findeAlleMitBetragKleiner(Double betrag);
    int liefereAnzahlEintraege();
}
