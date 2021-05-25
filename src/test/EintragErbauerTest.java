package test;

import main.model.Art;
import main.model.Eintrag;
import main.model.EintragErbauer;
import main.model.Kategorie;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EintragErbauerTest {

    public EintragErbauerTest() {

    }

    @Before
    public void setup() {

    }

    @Test
    public void erstelleDefaultEintrag() {
        // Arrange
        Eintrag erhaltenerEintrag;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        Date datum;
        try {
            datum = formatter.parse("13.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
            datum = new Date();

        }
        // Act
        erhaltenerEintrag = new EintragErbauer().build();

        // Assert
        assertThat(erhaltenerEintrag.getBezeichnung(), is("Testausgabe"));
        assertThat(erhaltenerEintrag.getBeschreibung(), is("Ich bin eine Testbeschreibung"));
        assertThat(erhaltenerEintrag.getBetrag(), is(59.99));
        assertThat(erhaltenerEintrag.getArt(), is(Art.Ausgabe));
        assertThat(erhaltenerEintrag.getKategorie().getBezeichnung(), is("Lebensmittel"));
        assertThat(erhaltenerEintrag.getProduktliste(), is("Kartoffeln, Milch, Shampoo, Toilettenpapier"));
        assertThat(erhaltenerEintrag.getDatum(), is(datum));
    }

    @Test
    public void erstelleEintragMitAllenEigenschaften() {
        // Arrange
        String bezeichnung = "Neue Bezeichnung";
        String beschreibung = "Neue Beschreibung";
        Double betrag = 100.0;
        Art art = Art.Einnahme;
        Kategorie kategorie = new Kategorie("Neue Kategorie", "Ich bin die Beschreibung einer neuen Kategorie.");
        String produktliste = "Produkt, noch ein Produkt";
        String datum = "20.05.2021";
        Eintrag erhaltenerEintrag;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        Date datum1;
        try {
            datum1 = formatter.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
            datum1 = new Date();

        }

        // Act
        erhaltenerEintrag = new EintragErbauer().mitAllenEigenschaften(bezeichnung, beschreibung, betrag, art, kategorie, datum, produktliste).build();

        // Assert
        assertThat(erhaltenerEintrag.getBezeichnung(), is(bezeichnung));
        assertThat(erhaltenerEintrag.getBeschreibung(), is(beschreibung));
        assertThat(erhaltenerEintrag.getBetrag(), is(betrag));
        assertThat(erhaltenerEintrag.getArt(), is(art));
        assertThat(erhaltenerEintrag.getKategorie().getBezeichnung(), is(kategorie.getBezeichnung()));
        assertThat(erhaltenerEintrag.getProduktliste(), is(produktliste));
        assertThat(erhaltenerEintrag.getDatum(), is(datum1));
    }


}
