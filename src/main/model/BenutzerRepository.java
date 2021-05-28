package main.model;

import java.util.Optional;
import java.util.UUID;

public interface BenutzerRepository {

    void fuegeBenutzerHinzu(Benutzer benutzer) throws Exception;
    void entferne(Benutzer benutzer);
    boolean pruefeObVorhanden(Benutzer benutzer);
    Optional<Benutzer> findeÜberId(UUID id);
    Optional<Benutzer> findeÜberNamen(String vorname, String nachname);
    Iterable<Benutzer> findeAlle();
}
