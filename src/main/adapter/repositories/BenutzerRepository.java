package main.adapter.repositories;

import main.model.Benutzer;
import java.util.HashSet;
import java.util.Optional;

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
        return this.alleBenutzer.contains(benutzer);
    }

    @Override
    public Optional<Benutzer> findeÜberId(String id) {
        for(Benutzer benutzer : this.alleBenutzer) {
            if(id == benutzer.getId()){
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
