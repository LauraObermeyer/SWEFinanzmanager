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
import static org.mockito.Mockito.when;

public class EintraegeAnzeigenAdapterTest {

    private List<Eintrag> eintraege;
    private UUID id1;
    private UUID id2;
    private Eintrag eintrag1;
    private Eintrag eintrag2;
    private Date datum;

    public EintraegeAnzeigenAdapterTest () { }

    @Before
    public void setUp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        try {
            this.datum = formatter.parse("13.10.2020");
        } catch (ParseException e) {
            e.printStackTrace();
            this.datum = new Date();
        }
        this.eintraege  = new ArrayList<Eintrag>();
        this.id1 = UUID.randomUUID();
        this.id2 = UUID.randomUUID();
        this.eintrag1 = new Eintrag(id1, "Einnahme1", "Miete", 600, Art.Einnahme, new Kategorie("Einkauf"), this.datum, "Essen");
        this.eintrag2 = new Eintrag(id2, "Ausgabe1", "Möbel", 1000, Art.Ausgabe, new Kategorie("Einkauf"), this.datum, "Möbel");
        this.eintraege.add(eintrag1);
        this.eintraege.add(eintrag2);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void tabellenInhaltAufbauenMitVorhandenenEintraegen() {
        //Arrange
        EintragRepository eintragVerwaltungMock = Mockito.mock(EintragRepository.class);
        when(eintragVerwaltungMock.liefereAnzahlEintraege()).thenReturn(this.eintraege.size());

        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(eintragVerwaltungMock);
        EintraegeAnzeigenAdapter spy= Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(this.eintraege).when(spy).getEintraege();

        //Act
        String[][] tabellenInhalt = spy.tabelleninhaltAufbauen();

        //Assert
        assertThat(tabellenInhalt.length, is(this.eintraege.size()));
        Mockito.verify(eintragVerwaltungMock, Mockito.times(2)).liefereAnzahlEintraege();
    }

    @Test
    public void tabellenInhaltAufbauenOhneVorhandeneEintraege() {
        //Arrange
        List<Eintrag> listeOhneEintraege  = new ArrayList<Eintrag>();

        EintragRepository eintragVerwaltungMock = Mockito.mock(EintragRepository.class);
        when(eintragVerwaltungMock.liefereAnzahlEintraege()).thenReturn(listeOhneEintraege.size());

        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(eintragVerwaltungMock);
        EintraegeAnzeigenAdapter spy = Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(listeOhneEintraege).when(spy).getEintraege();

        //Act
        String[][] tabellenInhalt = spy.tabelleninhaltAufbauen();

        //Assert
        assertThat(tabellenInhalt.length, is(0));
        Mockito.verify(eintragVerwaltungMock, Mockito.times(1)).liefereAnzahlEintraege();
    }

    @Test
    public void tabelleninhaltZeilenweiseFüllen() {
        // Arrange
        String[][] tabellenInhalt = new String[this.eintraege.size()][10];
        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(new EintragRepository());

        EintraegeAnzeigenAdapter spy = Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(this.eintraege).when(spy).getEintraege();

        // Act
        String[][] tabellenInhalt1 = spy.tabelleninhaltZeilenweiseFüllen(tabellenInhalt);

        // Assert
        assertThat(tabellenInhalt1[0][0], is(this.id1.toString()));
        assertThat(tabellenInhalt1[0][1], is("Einnahme1"));
        assertThat(tabellenInhalt1[0][2], is("600.0"));
        assertThat(tabellenInhalt1[0][3], is("Einnahme"));
        assertThat(tabellenInhalt1[0][4], is("Einkauf"));
        assertThat(tabellenInhalt1[0][5], is("13.10.2020"));
        assertThat(tabellenInhalt1[0][6], is("Essen"));
        assertThat(tabellenInhalt1[0][7], is(">"));

        assertThat(tabellenInhalt1[1][0], is(this.id2.toString()));
        assertThat(tabellenInhalt1[1][1], is("Ausgabe1"));
        assertThat(tabellenInhalt1[1][2], is("1000.0"));
        assertThat(tabellenInhalt1[1][3], is("Ausgabe"));
        assertThat(tabellenInhalt1[1][4], is("Einkauf"));
        assertThat(tabellenInhalt1[1][5], is("13.10.2020"));
        assertThat(tabellenInhalt1[1][6], is("Möbel"));
        assertThat(tabellenInhalt[1][7], is(">"));
    }
}
