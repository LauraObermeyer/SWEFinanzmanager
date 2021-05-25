package test.adapter;

import main.adapter.EintraegeDetailansichtAdapter;
import main.model.Eintrag;
import main.adapter.EintragErbauer;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EintraegeDetailansichtAdapterTest {

    public EintraegeDetailansichtAdapterTest () {

    }

    @Test
    public void getEigenschaften() {
        // Arrange
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        Eintrag eintragZumTesten = new EintragErbauer().build();
        EintraegeDetailansichtAdapter eintraegeDetailansichtAdapter = new EintraegeDetailansichtAdapter();

        // Act
        String eigenschaften[] = eintraegeDetailansichtAdapter.getEigenschaften(eintragZumTesten);

        //Assert
        assertThat(eigenschaften[0], is("Id: " + eintragZumTesten.getId()));
        assertThat(eigenschaften[1], is("Bezeichnung: " + eintragZumTesten.getBezeichnung()));
        assertThat(eigenschaften[2], is("Beschreibung: " + eintragZumTesten.getBeschreibung()));
        assertThat(eigenschaften[3], is("Betrag: " + eintragZumTesten.getBetrag() + "€"));
        assertThat(eigenschaften[4], is("Art: " + eintragZumTesten.getArt().toString()));
        assertThat(eigenschaften[5], is("Kategorie: " + eintragZumTesten.getKategorie().getBezeichnung()));
        assertThat(eigenschaften[6], is("Datum: " + formatter.format(eintragZumTesten.getDatum())));
        assertThat(eigenschaften[7], is("Produktliste: " + eintragZumTesten.getProduktliste()));
        assertThat(eigenschaften[8], is( "Systemaenderung: " + eintragZumTesten.getSystemaenderung().getZeitstempel()));
    }
}
