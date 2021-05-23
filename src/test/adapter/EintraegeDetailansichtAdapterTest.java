package test.adapter;

import main.adapter.EintraegeDetailansichtAdapter;
import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EintraegeDetailansichtAdapterTest {

    public EintraegeDetailansichtAdapterTest () {
    }

    @Test
    public void getEigenschaften() {
        // Arrange
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        Date datum;
        try {
            datum = formatter.parse("13.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
            datum = new Date();
        }

        UUID id = UUID.randomUUID();
        Eintrag eintrag = new Eintrag(id, "Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), datum, "Essen");
        EintraegeDetailansichtAdapter eintraegeDetailansichtAdapter = new EintraegeDetailansichtAdapter();

        // Act
        String eigenschaften[] = eintraegeDetailansichtAdapter.getEigenschaften(eintrag);

        //Assert
        assertThat(eigenschaften[0], is("Id: " + id));
        assertThat(eigenschaften[1], is("Bezeichnung: Einnahme1"));
        assertThat(eigenschaften[2], is("Beschreibung: Miete"));
        assertThat(eigenschaften[3], is("Betrag: 600.0€"));
        assertThat(eigenschaften[4], is("Art: Einnahme"));
        assertThat(eigenschaften[5], is("Kategorie: Einkauf"));
        assertThat(eigenschaften[6], is("Datum: 13.10.2020"));
        assertThat(eigenschaften[7], is("Produktliste: Essen"));
        // TODO: Besser testen kann man die Systemänderung wahrscheinlich nicht, weil wir den Wert ja nicht kennen, oder?
        assertThat(eigenschaften[8], is( "Systemaenderung: " + eintrag.getSystemaenderung().getZeitstempel()));
    }
}
