package test.adapter;

import main.adapter.EintraegeAnzeigenAdapter;
import main.adapter.repositories.EintragRepository;
import main.model.Eintrag;
import main.model.EintragErbauer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class EintraegeAnzeigenAdapterTest {

    private List<Eintrag> eintraege;
    private Eintrag ersterEintragZumTesten;
    private Eintrag zweiterEintragZumTesten;
    private SimpleDateFormat formatter;

    public EintraegeAnzeigenAdapterTest () { }

    @Before
    public void setUp() {
        formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        this.eintraege  = new ArrayList<Eintrag>();
        this.ersterEintragZumTesten = new EintragErbauer().build();
        this.zweiterEintragZumTesten = new EintragErbauer().build();
        this.eintraege.add(ersterEintragZumTesten);
        this.eintraege.add(zweiterEintragZumTesten);
    }

    @After
    public void tearDown() {
        this.eintraege.clear();
    }

    @Test
    public void tabellenInhaltAufbauenMitVorhandenenEintraegen() {
        //Arrange
        EintragRepository eintragVerwaltungMock = Mockito.mock(EintragRepository.class);
        when(eintragVerwaltungMock.liefereAnzahlEintraege()).thenReturn(this.eintraege.size());

        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(eintragVerwaltungMock);
        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapterSpy = Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(this.eintraege).when(eintraegeAnzeigenAdapterSpy).getEintraege();

        //Act
        String[][] tabellenInhalt = eintraegeAnzeigenAdapterSpy.tabelleninhaltAufbauen();

        //Assert
        assertThat(tabellenInhalt.length, is(this.eintraege.size()));
        Mockito.verify(eintragVerwaltungMock, Mockito.times(2)).liefereAnzahlEintraege();
        Mockito.verify(eintraegeAnzeigenAdapterSpy).getEintraege();
    }

    @Test
    public void tabellenInhaltAufbauenOhneVorhandeneEintraege() {
        //Arrange
        List<Eintrag> listeOhneEintraege  = new ArrayList<Eintrag>();

        EintragRepository eintragVerwaltungMock = Mockito.mock(EintragRepository.class);
        when(eintragVerwaltungMock.liefereAnzahlEintraege()).thenReturn(listeOhneEintraege.size());

        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(eintragVerwaltungMock);
        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapterSpy = Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(listeOhneEintraege).when(eintraegeAnzeigenAdapterSpy).getEintraege();

        //Act
        String[][] tabellenInhalt = eintraegeAnzeigenAdapterSpy.tabelleninhaltAufbauen();

        //Assert
        assertThat(tabellenInhalt.length, is(0));
        Mockito.verify(eintragVerwaltungMock, Mockito.times(1)).liefereAnzahlEintraege();
        Mockito.verify(eintraegeAnzeigenAdapterSpy).getEintraege();
    }

    @Test
    public void tabelleninhaltZeilenweiseFüllen() {
        // Arrange
        String[][] tabellenInhalt = new String[this.eintraege.size()][10];
        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapter = new EintraegeAnzeigenAdapter(new EintragRepository());

        EintraegeAnzeigenAdapter eintraegeAnzeigenAdapterSpy = Mockito.spy(eintraegeAnzeigenAdapter);
        Mockito.doReturn(this.eintraege).when(eintraegeAnzeigenAdapterSpy).getEintraege();

        // Act
        String[][] tabellenInhalt1 = eintraegeAnzeigenAdapterSpy.tabelleninhaltZeilenweiseFüllen(tabellenInhalt);

        // Assert
        assertThat(tabellenInhalt1[0][0], is(this.ersterEintragZumTesten.getId().toString()));
        assertThat(tabellenInhalt1[0][1], is(this.ersterEintragZumTesten.getBezeichnung()));
        assertThat(tabellenInhalt1[0][2], is(String.valueOf(this.ersterEintragZumTesten.getBetrag())));
        assertThat(tabellenInhalt1[0][3], is(String.valueOf(this.ersterEintragZumTesten.getArt())));
        assertThat(tabellenInhalt1[0][4], is(this.ersterEintragZumTesten.getKategorie().getBezeichnung()));
        assertThat(tabellenInhalt1[0][5], is(formatter.format(this.ersterEintragZumTesten.getDatum())));
        assertThat(tabellenInhalt1[0][6], is(this.ersterEintragZumTesten.getProduktliste()));
        assertThat(tabellenInhalt1[0][7], is(">"));

        assertThat(tabellenInhalt1[1][0], is(this.zweiterEintragZumTesten.getId().toString()));
        assertThat(tabellenInhalt1[1][1], is(this.zweiterEintragZumTesten.getBezeichnung()));
        assertThat(tabellenInhalt1[1][2], is(String.valueOf(this.zweiterEintragZumTesten.getBetrag())));
        assertThat(tabellenInhalt1[1][3], is(String.valueOf(this.zweiterEintragZumTesten.getArt())));
        assertThat(tabellenInhalt1[1][4], is(this.zweiterEintragZumTesten.getKategorie().getBezeichnung()));
        assertThat(tabellenInhalt1[1][5], is(formatter.format(this.zweiterEintragZumTesten.getDatum())));
        assertThat(tabellenInhalt1[1][6], is(this.zweiterEintragZumTesten.getProduktliste()));
        assertThat(tabellenInhalt1[1][7], is(">"));
        Mockito.verify(eintraegeAnzeigenAdapterSpy).getEintraege();
    }
}
