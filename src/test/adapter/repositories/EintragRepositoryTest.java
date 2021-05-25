package test.adapter.repositories;

import main.adapter.repositories.EintragRepository;
import main.model.Art;
import main.model.Eintrag;
import main.model.EintragErbauer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EintragRepositoryTest {

    private HashSet<Eintrag> eintraegeZumTesten;
    private Eintrag ersterEintragZumTesten;
    private Eintrag zweiterEintragZumTesten;
    private EintragRepository eintragRepositoryZumTestenSpy;

    public EintragRepositoryTest(){ }

    @Before
    public void setup() {
        this.ersterEintragZumTesten = new EintragErbauer().build();
        this.zweiterEintragZumTesten = new EintragErbauer().build();
        this.eintraegeZumTesten = new HashSet<Eintrag>();
        this.eintraegeZumTesten.add(this.ersterEintragZumTesten);
        this.eintraegeZumTesten.add(this.zweiterEintragZumTesten);

        this.eintragRepositoryZumTestenSpy = Mockito.spy(new EintragRepository());
        Mockito.doReturn(this.eintraegeZumTesten).when(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @After
    public void teardown() {
        this.eintraegeZumTesten.clear();
    }

    @Test
    public void liefereAnzahlEintraegeBeiVorhandenenEintraegen(){
        // Arrange
        int erwarteteAnzahlEintraege = this.eintraegeZumTesten.size();
        int erhalteneAnzahlEintraege;

        // Act
        erhalteneAnzahlEintraege = this.eintragRepositoryZumTestenSpy.liefereAnzahlEintraege();

        // Assert
        assertThat(erhalteneAnzahlEintraege, is(erwarteteAnzahlEintraege));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void liefereAnzahlEintraegeBeiKeinenEintraegen(){
        // Arrange
        this.eintraegeZumTesten.clear();
        int erwarteteAnzahlEintraege = this.eintraegeZumTesten.size();
        int erhalteneAnzahlEintraege;

        // Act
        erhalteneAnzahlEintraege = this.eintragRepositoryZumTestenSpy.liefereAnzahlEintraege();

        // Assert
        assertThat(erhalteneAnzahlEintraege, is(erwarteteAnzahlEintraege));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void entferneEintrag() {
        // Arrange
        int erwarteteAnzahlEintraege = this.eintraegeZumTesten.size() - 1;

        // Act
        this.eintragRepositoryZumTestenSpy.entferne(this.ersterEintragZumTesten);
        List nachEntfernungVorhandeneEintraege = new ArrayList<Eintrag>();
        for(Eintrag eintrag: this.eintraegeZumTesten) {
            nachEntfernungVorhandeneEintraege.add(eintrag);
        }

        // Assert
        assertThat(nachEntfernungVorhandeneEintraege.size(), is(erwarteteAnzahlEintraege));
        assertThat(nachEntfernungVorhandeneEintraege.get(0), is(this.zweiterEintragZumTesten));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void pruefeObVorhandenerEintragVorhanden() {
        // Arrange
        boolean erwarteteRueckgabe = true;
        boolean erhalteneRueckgabe;

        // Act
        erhalteneRueckgabe = this.eintragRepositoryZumTestenSpy.pruefeObVorhanden(this.ersterEintragZumTesten);

        // Assert
        assertThat(erhalteneRueckgabe, is(erwarteteRueckgabe));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void pruefeObNichtVorhandenerEintragVorhanden() {
        // Arrange
        Eintrag nichtVorhandenerEintrag = new EintragErbauer().build();
        boolean erwarteteRueckgabe = false;
        boolean erhalteneRueckgabe;

        // Act
        erhalteneRueckgabe = this.eintragRepositoryZumTestenSpy.pruefeObVorhanden(nichtVorhandenerEintrag);

        // Assert
        assertThat(erhalteneRueckgabe, is(erwarteteRueckgabe));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void fuegeNeuenEintragHinzu() {
        // Arrange
        Eintrag neuerEintrag = new EintragErbauer().build();
        int erwarteteAnzahlEintraege = this.eintraegeZumTesten.size() + 1;

        // Act
        this.eintragRepositoryZumTestenSpy.fuegeHinzu(neuerEintrag);
        List nachHinzufuegenVorhandeneEintraege = new ArrayList<Eintrag>();
        for(Eintrag vorhandenerEintrag: this.eintraegeZumTesten) {
            nachHinzufuegenVorhandeneEintraege.add(vorhandenerEintrag);
        }

        // Assert
        assertThat(nachHinzufuegenVorhandeneEintraege.size(), is(erwarteteAnzahlEintraege));
        assertThat(nachHinzufuegenVorhandeneEintraege.contains(this.ersterEintragZumTesten), is(true));
        assertThat(nachHinzufuegenVorhandeneEintraege.contains(this.zweiterEintragZumTesten), is(true));
        assertThat(nachHinzufuegenVorhandeneEintraege.contains(neuerEintrag), is(true));
        Mockito.verify(this.eintragRepositoryZumTestenSpy, Mockito.times(2)).findeAlle();
    }

    @Test
    public void fuegeVorhandenenEintragHinzu() {
        // Arrange
        int erwarteteAnzahlEintraege = this.eintraegeZumTesten.size();

        // Act
        this.eintragRepositoryZumTestenSpy.fuegeHinzu(this.ersterEintragZumTesten);
        List nachHinzufuegenVorhandeneEintraege = new ArrayList<Eintrag>();
        for(Eintrag vorhandenerEintrag: this.eintraegeZumTesten) {
            nachHinzufuegenVorhandeneEintraege.add(vorhandenerEintrag);
        }

        // Assert
        assertThat(nachHinzufuegenVorhandeneEintraege.size(), is(erwarteteAnzahlEintraege));
        assertThat(nachHinzufuegenVorhandeneEintraege.contains(this.ersterEintragZumTesten), is(true));
        assertThat(nachHinzufuegenVorhandeneEintraege.contains(this.zweiterEintragZumTesten), is(true));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void findeAlleEintraegeMitBetragGroeßerAlsWert() {
        // Arrange
        double gesetzterBetrag = 500.0;
        this.ersterEintragZumTesten.setBetrag(1122.0);
        //Eintrag neuerEintragMitGrossemBetrag = new EintragErbauer().mitBetrag(1199.5).build();
        int anzahlErwarteterEintraege = 1;
        //this.eintraegeZumTesten.add(neuerEintragMitGrossemBetrag);*/

        List erhalteneEintraege = new ArrayList<Eintrag>();

        // Act
        Iterable<Eintrag> eintraege = this.eintragRepositoryZumTestenSpy.findeAlleMitBetragGroeßer(gesetzterBetrag);
        for (Eintrag eintrag: eintraege) {
            erhalteneEintraege.add(eintrag);
        }

        // Assert
        assertThat(erhalteneEintraege.size(), is(anzahlErwarteterEintraege));
        assertThat(erhalteneEintraege.contains(this.ersterEintragZumTesten), is(true));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void findeAlleAusgaben() {
        // Arrange
        int erwarteteAnzahlEintraege = 2;
        List erhalteneEintraege = new ArrayList<Eintrag>();

        // Act
        Iterable<Eintrag> eintraege = this.eintragRepositoryZumTestenSpy.findeAlleNach(Art.Ausgabe);
        for (Eintrag eintrag: eintraege) {
            erhalteneEintraege.add(eintrag);
        }

        // Assert
        assertThat(erhalteneEintraege.size(), is(erwarteteAnzahlEintraege));
        assertThat(erhalteneEintraege.contains(this.ersterEintragZumTesten), is(true));
        assertThat(erhalteneEintraege.contains(this.zweiterEintragZumTesten), is(true));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void findeAlleEinnahmenOhneEinnahmenInListe() {
        // Arrange
        int erwarteteAnzahlEintraege = 0;
        List erhalteneEintraege = new ArrayList<Eintrag>();

        // Act
        Iterable<Eintrag> eintraege = this.eintragRepositoryZumTestenSpy.findeAlleNach(Art.Einnahme);
        for (Eintrag eintrag: eintraege) {
            erhalteneEintraege.add(eintrag);
        }

        // Assert
        assertThat(erhalteneEintraege.size(), is(erwarteteAnzahlEintraege));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void findeAlleEinnahmen() {
        // Arrange
        int erwarteteAnzahlEintraege = 1;
        List erhalteneEintraege = new ArrayList<Eintrag>();
        Eintrag eintragEinnahme = new EintragErbauer().mitArt(Art.Einnahme).build();
        this.eintraegeZumTesten.add(eintragEinnahme);

        // Act
        Iterable<Eintrag> eintraege = this.eintragRepositoryZumTestenSpy.findeAlleNach(Art.Einnahme);
        for (Eintrag eintrag: eintraege) {
            erhalteneEintraege.add(eintrag);
        }

        // Assert
        assertThat(erhalteneEintraege.size(), is(erwarteteAnzahlEintraege));
        assertThat(erhalteneEintraege.contains(eintragEinnahme), is(true));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void findeEintragMitBezeichnung() {
        // Arrange
        String bezeichnungDesEintrags = "EintragZumTesten";
        Eintrag eintragMitBezeichnung = new EintragErbauer().mitBezeichnung(bezeichnungDesEintrags).build();
        this.eintraegeZumTesten.add(eintragMitBezeichnung);

        // Act
        Optional<Eintrag> erhaltenerEintrag = this.eintragRepositoryZumTestenSpy.findeÜber(bezeichnungDesEintrags);

        // Assert
        assertThat(erhaltenerEintrag.isPresent(), is(true));
        assertThat(erhaltenerEintrag.get(), is(eintragMitBezeichnung));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }

    @Test
    public void findeEintragMitNichtVorhandenerBezeichnung() {
        // Arrange
        String bezeichnungDesEintrags = "EintragZumTesten";

        // Act
        Optional<Eintrag> erhaltenerEintrag = this.eintragRepositoryZumTestenSpy.findeÜber(bezeichnungDesEintrags);

        // Assert
        assertThat(erhaltenerEintrag.isEmpty(), is(true));
        Mockito.verify(this.eintragRepositoryZumTestenSpy).findeAlle();
    }
}
