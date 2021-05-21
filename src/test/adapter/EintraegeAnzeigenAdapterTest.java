package test.adapter;

import main.adapter.EintraegeAnzeigenAdapter;
import main.adapter.repositories.EintragRepository;
import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

        List<Eintrag> eintraegeList  = new ArrayList<Eintrag>();
        Eintrag eintrag1 = new Eintrag(UUID.randomUUID(), "Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), datum, "Essen");
        Eintrag eintrag2 = new Eintrag(UUID.randomUUID(), "Ausgabe1", "Möbel", 1000, Art.Ausgabe, new Kategorie("Einkauf"), datum, "Möbel");
        eintraegeList.add(eintrag1);
        eintraegeList.add(eintrag2);

        // Wäre die getEintraege Methode nicht static, sollte das so funktionieren.
        // EintraegeAnzeigenAdapter adapterMock = mock(EintraegeAnzeigenAdapter.class);
        // when(adapterMock.getEintraege()).thenReturn(eintraege);

        // TODO: Bin mir nicht sicher, ob Mockito static Methoden mocken kann. Dieser Ansatz hat nicht funktioniert.
        // TODO: Dafür musste ich das Sprachlevel schon von 5 auf 8 setzen und Mockito-inline anstatt mockito-core als dependency hinzufügen
        // MockedStatic<EintraegeAnzeigenAdapter> adapterMock = Mockito.mockStatic(EintraegeAnzeigenAdapter.class);
        // adapterMock.when(EintraegeAnzeigenAdapter::getEintraege).thenReturn(eintraege);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void tabelleninhaltZeilenweiseFüllen() {
        // Arrange
        List<Eintrag> eintraege  = new ArrayList<Eintrag>();
        Eintrag eintrag1 = new Eintrag(UUID.randomUUID(), "Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), datum, "Essen");
        Eintrag eintrag2 = new Eintrag(UUID.randomUUID(), "Ausgabe1", "Möbel", 1000, Art.Ausgabe, new Kategorie("Einkauf"), datum, "Möbel");
        eintraege.add(eintrag1);
        eintraege.add(eintrag2);

        String[][] tabellenInhalt = new String[eintraege.size()][10];
        EintragRepository eintragVerwaltung = new EintragRepository();
        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(eintragVerwaltung);

        // Act
        String[][] tabellenInhalt1 = eintraegeAnzeigenAdapter.tabelleninhaltZeilenweiseFüllen(tabellenInhalt);

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
