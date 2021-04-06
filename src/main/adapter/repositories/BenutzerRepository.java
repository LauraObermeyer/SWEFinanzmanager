package main.adapter.repositories;

import main.model.Benutzer;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class BenutzerRepository implements main.model.BenutzerRepository {
    private HashSet<Benutzer> alleBenutzer = new HashSet<Benutzer>();
    public BenutzerRepository(){

    }
    @Override
    public void fuegeBenutzerHinzu(Benutzer benutzer) throws Exception {
        if(this.pruefeObVorhanden( benutzer ) )
            throw new Exception( "Benutzer existiert bereits! " );
        this.alleBenutzer.add(benutzer);
    }

    @Override
    public void entferne(Benutzer benutzer) {
        this.alleBenutzer.remove(benutzer);
    }

    @Override
    public boolean pruefeObVorhanden(Benutzer benutzer) {
        for(Benutzer vorhandenerBenutzer : this.alleBenutzer) {
            int value = vorhandenerBenutzer.getId().compareTo(benutzer.getId());
            if(value == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Benutzer> findeÜberId(UUID id) {
        for(Benutzer benutzer : this.alleBenutzer) {
            if(id.compareTo(benutzer.getId()) == 0) {
                return Optional.of(benutzer);
            }
        }
        return Optional.empty();
    }


    @Override
    public Iterable<Benutzer> findeAlle() {
        return this.alleBenutzer;
    }
}
