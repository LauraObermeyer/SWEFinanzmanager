package main.model;

import java.util.Optional;

public interface BenutzerRepository {

    void fuegeBenutzerHinzu(Benutzer benutzer) throws Exception;
    void entferne(Benutzer benutzer);
    boolean pruefeObVorhanden(Benutzer benutzer);
    Optional<Benutzer> findeÜberId(String id);
    Iterable<Benutzer> findeAlle();
}
