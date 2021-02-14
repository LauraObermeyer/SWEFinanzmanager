package main.gui.Ausgaben;

import main.adapter.AusgabenDetailansichtAdapter;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.model.Eintrag;
import main.util.CSVReader;
import main.util.CSVWriter;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AusgabenDetailansichtGUI extends JPanel implements IGUIEventSender {

    // "eintrag" ist das Eintrag-Objekt, zu dem die Detailansicht gehört
    private Eintrag eintrag;

    // Zurück-Button, um zur Übersicht zurück zu kehren
    private JButton jbZurückButton;

    private JPanel jpTabHeader;

    // Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    // CSVReader und Writer
    private CSVReader csvReader;
    private CSVWriter csvWriter;

    // Header für Eintrags-Datei
    private static final String[] header = Eintrag.getAlleAttributnamen();

    public AusgabenDetailansichtGUI(Eintrag eintrag){
        this.eintrag = eintrag;
        this.setLayout(new BorderLayout());

        // Methoden zum Aufbau der Detailansicht aufrufen
        buildTabHeader();
        buildEigenschaftenPanel();
        buildFooterButtons();
    }

    private void buildTabHeader() {
        jpTabHeader = new JPanel();
        // Layout setzen
        jpTabHeader.setLayout(new BorderLayout());

        JPanel jpTabHeaderLinks = new JPanel();
        // Komponenten linksbündig ausrichten
        jpTabHeaderLinks.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Zurück-Button, um zur Übersicht zurück zu kehren
        jbZurückButton = new JButton("<");

        // ActionListener für Zurück-Button, damit man zur Übersicht zurück kehren kann
        jbZurückButton.addActionListener(e -> fireEvent(new GUIEvent("ZurueckZuUebersicht", this)));

        // linke Seite des Headers
        JPanel jpTextLinks = new JPanel();
        jpTextLinks.setLayout(new GridLayout(2, 1));

        // Zurück-Button und linkes Text-Panel zum linken Teil des Headers der Detailansicht hinzufügen
        jpTabHeaderLinks.add(jbZurückButton);
        jpTabHeaderLinks.add(jpTextLinks);

        // linken Teil des Headers zum Header der Detailansicht hinzufügen
        // BorderLayout.WEST, da linker Teil des Headers links sein soll
        jpTabHeader.add(jpTabHeaderLinks, BorderLayout.WEST);

        // Panel für rechte Seite des Headers mit Bearbeiten- und Löschen-Buttons
        JPanel headerRechts = new JPanel(new GridLayout(2,1));

        // horizontale Trennlinie unter TabHeader (unter Inventarnr und Löschen Button)
        JSeparator jsTrennlinieTabHeader = new JSeparator();
        jsTrennlinieTabHeader.setOrientation(SwingConstants.HORIZONTAL);
        jpTabHeader.add(jsTrennlinieTabHeader, BorderLayout.SOUTH);

        // Bearbeiten- und Löschen-Button für linke Seite des des Headers der Detailansicht bauen
        buildHeaderButtons();

        // Header der Detailansicht zum Haupt-Panel hinzufügen
        this.add(jpTabHeader, BorderLayout.NORTH);
    }

    private void buildHeaderButtons() {
        // Bearbeiten- und Löschen-Button für linke Seite des des Headers der Detailansicht bauen
        JButton jbBearbeiten = new JButton("Bearbeiten");
        JButton jbLöschen = new JButton("Löschen");

        jbBearbeiten.addActionListener(e -> fireEvent(new GUIEvent("Bearbeiten", this)));
        jbLöschen.addActionListener(e -> fireEvent(new GUIEvent("Löschen", this)));

        // Bearbeiten- und Löschen-Button hinzufügen
        JPanel jpButtons = new JPanel(new GridLayout(2, 1));
        jpButtons.add(jbBearbeiten);
        jpButtons.add(jbLöschen);
        jpTabHeader.add(jpButtons, BorderLayout.EAST);
    }

    private void buildEigenschaftenPanel() {
        // in dem "jpEigenschaften"-Panel sollen die Eigenschaften des Eintrags angezeigt werden
        JPanel jpEigenschaften = new JPanel();
        // Layout des "jpEigenschaften"-Panel setzen
        jpEigenschaften.setLayout(new GridLayout(17, 1));

        String eigenschaften[] = AusgabenDetailansichtAdapter.getEigenschaften(eintrag);
        for(int i = 0; i < eigenschaften.length; i++){
            jpEigenschaften.add(new JLabel(eigenschaften[i]));
        }

        // Eigenschafts-Panel zum Hauptpanel hinzufügen (links angeordnet, deshalb BorderLayout.WEST)
        this.add(jpEigenschaften, BorderLayout.WEST);
    }

    private void buildFooterButtons(){
        JButton jbBearbeiten = new JButton("Import");
        JButton jbLöschen = new JButton("Export");

        jbBearbeiten.addActionListener(e -> fireEvent(new GUIEvent("Import", this)));
        jbLöschen.addActionListener(e -> fireEvent(new GUIEvent("Export", this)));

        JPanel jpButtons = new JPanel();
        jpButtons.add(jbBearbeiten);
        jpButtons.add(jbLöschen);
        this.add(jpButtons, BorderLayout.SOUTH);
    }

    public Eintrag getEintrag() {
        return eintrag;
    }

    public void setEintrag(Eintrag eintrag) {
        this.eintrag = eintrag;
    }

    @Override
    public void fireEvent(GUIEvent event) {
        // GUIController triggern
        listeners.forEach( listener -> listener.eventFired(event));
    }

    @Override
    public void addListener(IGUIEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IGUIEventListener listener) {
        listeners.remove(listener);
    }
}
