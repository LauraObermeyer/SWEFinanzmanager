package model;

import java.util.Optional;

public interface BenutzerRepository {

    void fuegeBenutzerHinzu(main.model.Benutzer benutzer) throws Exception;
    void entferne(main.model.Benutzer benutzer);
    boolean pruefeObVorhanden(main.model.Benutzer benutzer);
    Optional<main.model.Benutzer> findeÜberId(String id);
    Iterable<main.model.Benutzer> findeAlle();
}
