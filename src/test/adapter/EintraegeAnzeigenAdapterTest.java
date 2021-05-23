package test.adapter;

import main.adapter.EintraegeAnzeigenAdapter;
import main.adapter.repositories.EintragRepository;
import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EintraegeAnzeigenAdapterTest {

    Date datum;
    public EintraegeAnzeigenAdapterTest () {
    }

    @Before
    public void setUp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        try {
            this.datum = formatter.parse("13.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
            this.datum = new Date();
        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void tabelleninhaltZeilenweiseFüllen() {
        // Arrange
        List<Eintrag> eintraege  = new ArrayList<Eintrag>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Eintrag eintrag1 = new Eintrag(id1, "Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), this.datum, "Essen");
        Eintrag eintrag2 = new Eintrag(id2, "Ausgabe1", "Möbel", 1000, Art.Ausgabe, new Kategorie("Einkauf"), this.datum, "Möbel");
        eintraege.add(eintrag1);
        eintraege.add(eintrag2);

        String[][] tabellenInhalt = new String[eintraege.size()][10];
        EintragRepository eintragVerwaltung = new EintragRepository();
        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(eintragVerwaltung);

        EintraegeAnzeigenAdapter spy= Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(eintraege).when(spy).getEintraege();

        // Act
        String[][] tabellenInhalt1 = spy.tabelleninhaltZeilenweiseFüllen(tabellenInhalt);

        // Assert
        assertThat(tabellenInhalt1[0][0], is(id1.toString()));
        assertThat(tabellenInhalt1[0][1], is("Einnahme1"));
        assertThat(tabellenInhalt1[0][2], is("600.0"));
        assertThat(tabellenInhalt1[0][3], is("Einnahme"));
        assertThat(tabellenInhalt1[0][4], is("Einkauf"));
        assertThat(tabellenInhalt1[0][5], is("13.10.2020"));
        assertThat(tabellenInhalt1[0][6], is("Essen"));
        assertThat(tabellenInhalt1[0][7], is(">"));

        assertThat(tabellenInhalt1[1][0], is(id2.toString()));
        assertThat(tabellenInhalt1[1][1], is("Ausgabe1"));
        assertThat(tabellenInhalt1[1][2], is("1000.0"));
        assertThat(tabellenInhalt1[1][3], is("Ausgabe"));
        assertThat(tabellenInhalt1[1][4], is("Einkauf"));
        assertThat(tabellenInhalt1[1][5], is("13.10.2020"));
        assertThat(tabellenInhalt1[1][6], is("Möbel"));
        assertThat(tabellenInhalt[1][7], is(">"));

    }
}
