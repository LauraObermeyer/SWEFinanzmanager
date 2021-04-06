package main.model;

import java.util.Optional;
import java.util.UUID;

public interface BenutzerRepository {

    void fuegeBenutzerHinzu(Benutzer benutzer) throws Exception;
    void entferne(Benutzer benutzer);
    boolean pruefeObVorhanden(Benutzer benutzer);
    Optional<Benutzer> findeÜberId(UUID id);
    Iterable<Benutzer> findeAlle();
}
