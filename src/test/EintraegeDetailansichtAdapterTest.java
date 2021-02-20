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
        // TODO: Datum so wie in getestetem Adapter konvertieren
        // assertThat(eigenschaften[5], is("Datum: 13.10.2020"));
        assertThat(eigenschaften[6], is("Produktliste: Essen"));
        // TODO: Format Zeitstempel
        // Zeitstempel kann noch nicht mitgetestet werden, da wie auch im EintraegeAnzeigenAdapter das Format noch richtig hinbekommen werden muss
        //assertThat(eigenschaften[7], is(Eintrag.getAlleAttributnamen()[7] + ": " + eintrag.getSystemaenderung().getZeitstempel()));
    }
}
