package model;

import java.util.Optional;

public interface BenutzerRepository {

    void fuegeBenutzerHinzu(main.model.Benutzer benutzer);
    void entferne(main.model.Benutzer benutzer);
    boolean pruefeObVorhanden(main.model.Benutzer benutzer);
    Optional<main.model.Benutzer> findeÜber(String vorname, String nachname);
    Iterable<main.model.Benutzer> findeAlle();
}
