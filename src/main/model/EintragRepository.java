package model;

import java.util.Optional;

public interface EintragRepository {
    void fuegeHinzu(main.model.Eintrag eintrag);
    void entferne(main.model.Eintrag eintrag);
    boolean pruefeObVorhanden(main.model.Eintrag eintrag);
    Iterable<main.model.Eintrag>findeAlle();
    Optional<main.model.Eintrag> findeÜber(String bezeichnung);
    Iterable<main.model.Eintrag>findeAlleAus(main.model.Kategorie kateogrie);
    Iterable<main.model.Eintrag>findeAlleNach(main.model.Art art);
    Iterable<main.model.Eintrag>findeAlleMitBetragGroeßer(Double betrag);
    Iterable<main.model.Eintrag>findeAlleMitBetragKleiner(Double betrag);
}
