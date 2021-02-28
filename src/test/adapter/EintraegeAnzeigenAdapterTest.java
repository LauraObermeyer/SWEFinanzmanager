package test.adapter;

import main.adapter.EintraegeAnzeigenAdapter;
import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;

public class EintraegeAnzeigenAdapterTest {

    Date datum;
    public EintraegeAnzeigenAdapterTest () {
    }

    @Before
    public void setUp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        try {
            datum = formatter.parse("13.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
            datum = new Date();
        }

        // TODO: Mockito oder anderes Mocking Framework einbinden, damit die eintrag Liste gemocked werden kann
        //List<Eintrag> mockedEintraege = mock(List.class);


        // when(mockedList.get(0)).thenReturn("first");
        List<Eintrag> eintraege  = new ArrayList<>();
        Eintrag eintrag1 = new  Eintrag("Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), datum, "Essen");
        Eintrag eintrag2 = new Eintrag("Ausgabe1", "Möbel", 1000, Art.Ausgabe, new Kategorie("Einkauf"), datum, "Möbel");
        eintraege.add(eintrag1);
        eintraege.add(eintrag2);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void tabelleninhaltZeilenweiseFüllen() {
        // Arrange
        List<Eintrag> eintraege  = new ArrayList<>();
        Eintrag eintrag1 = new  Eintrag("Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), datum, "Essen");
        Eintrag eintrag2 = new Eintrag("Ausgabe1", "Möbel", 1000, Art.Ausgabe, new Kategorie("Einkauf"), datum, "Möbel");
        eintraege.add(eintrag1);
        eintraege.add(eintrag2);

        String[][] tabellenInhalt = new String[eintraege.size()][10];

        // Act
        String[][] tabellenInhalt1 = EintraegeAnzeigenAdapter.tabelleninhaltZeilenweiseFüllen(tabellenInhalt);

        // Assert
        assertThat(tabellenInhalt1[0][0], is("Bezeichnung: Einnahme1"));
        assertThat(tabellenInhalt1[0][1], is("Beschreibung: Miete"));
        assertThat(tabellenInhalt1[0][2], is("Betrag: 600.0€"));
        assertThat(tabellenInhalt1[0][3], is("Art: Einnahme"));
        assertThat(tabellenInhalt1[0][4], is("Kategorie: Einkauf"));
        assertThat(tabellenInhalt1[0][5], is("Datum: 13.10.2020"));
        assertThat(tabellenInhalt1[0][6], is("Produktliste: Essen"));
        assertThat(tabellenInhalt1[0][7], is( "Systemaenderung: " + eintrag1.getSystemaenderung().getZeitstempel()));

        assertThat(tabellenInhalt1[1][0], is("Bezeichnung: Ausgabe1"));
        assertThat(tabellenInhalt1[1][1], is("Beschreibung: Möbel"));
        assertThat(tabellenInhalt1[1][2], is("Betrag: 1000.0€"));
        assertThat(tabellenInhalt1[1][3], is("Art: Ausgabe"));
        assertThat(tabellenInhalt1[1][4], is("Kategorie: Einkauf"));
        assertThat(tabellenInhalt1[1][5], is("Datum: 13.10.2020"));
        assertThat(tabellenInhalt1[1][6], is("Produktliste: Möbel"));
        assertThat(tabellenInhalt1[1][7], is( "Systemaenderung: " + eintrag2.getSystemaenderung().getZeitstempel()));
    }
}
