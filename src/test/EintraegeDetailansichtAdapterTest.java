package test;

import main.adapter.EintraegeDetailansichtAdapter;
import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EintraegeDetailansichtAdapterTest {

    public EintraegeDetailansichtAdapterTest () {
    }

    @Before
    public void setUp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
    }

    @After
    public void tearDown() {
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
        Eintrag eintrag = new Eintrag("Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), datum, "Essen");

        // Act
        String eigenschaften[] = EintraegeDetailansichtAdapter.getEigenschaften(eintrag);

        //Assert
        assertThat(eigenschaften[0], is("Bezeichnung: Einnahme1"));
        assertThat(eigenschaften[1], is("Beschreibung: Miete"));
        assertThat(eigenschaften[2], is("Betrag: 600.0€"));
        assertThat(eigenschaften[3], is("Art: Einnahme"));
        assertThat(eigenschaften[4], is("Kategorie: Einkauf"));
        assertThat(eigenschaften[5], is("Datum: 13.10.2020"));
        assertThat(eigenschaften[6], is("Produktliste: Essen"));
        // TODO: Besser testen kann man die Systemänderung wahrscheinlich nicht, weil wir den Wert ja nicht kennen, oder?
        assertThat(eigenschaften[7], is( "Systemaenderung: " + eintrag.getSystemaenderung().getZeitstempel()));
    }
}
