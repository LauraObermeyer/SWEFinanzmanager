package main.gui.Eintraege;

import main.adapter.EingebenAdapter;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class EingebenGUI implements IGUIEventSender {

    private JFrame jfEingebenFrame;
    private JPanel jpCenter, jpSouth;
    private JLabel jlBezeichnung, jlBeschreibung, jlArt, jlBetrag, jlKategorie, jlDatum, jlProduktliste;
    private JTextField jtfBezeichnung, jtfBeschreibung, jtfDatum, jtfProduktliste;
    private JFormattedTextField jtfBetrag;
    private JComboBox<String> jcKategorie, jcArt;

    //Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    public EingebenGUI() {
        jfEingebenFrame = new JFrame(this.getClass().getSimpleName());
        jfEingebenFrame.setTitle("Neuen Eintrag anlegen");
        jfEingebenFrame.setLayout(new BorderLayout(5,5));
        jfEingebenFrame.setVisible(true);
        jfEingebenFrame.setSize(400, 400);
        jfEingebenFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        buildBeschreibungstext();
        buildEingabefelder();
        buildButtons();
    }

    private void buildBeschreibungstext() {
        JLabel jlBeschreibungstext = new JLabel("Gib die Daten des neuen Eintrags an:");
        jfEingebenFrame.add(jlBeschreibungstext, BorderLayout.NORTH);
    }

    private void buildEingabefelder() {
        jpCenter = new JPanel(new GridLayout(7, 2));

        //Formatter, damit in Betrags-Feld nur Float Zahlen eingetragen werden können
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Float.class);
        formatter.setAllowsInvalid(false);

        jlBezeichnung = new JLabel("Bezeichnung:");
        jtfBezeichnung = new JTextField();
        jlBeschreibung = new JLabel("Beschreibung:");
        jtfBeschreibung = new JTextField();
        jlArt = new JLabel("Art:");
        String[] art = { "Einnahme", "Ausgabe"};
        jcArt = new JComboBox<String>(art);
        jlBetrag = new JLabel("Betrag:");
        jtfBetrag = new JFormattedTextField(formatter);
        jlKategorie = new JLabel("Kategorie:");
        String[] kategorien = { "Kategorie 1", "Kategorie 2", "Kategorie 3", "Kategorie 4", "Kategorie 5", "Kategorie 6"};
        jcKategorie = new JComboBox<String>(kategorien);
        jlDatum = new JLabel("Datum:");
        jtfDatum = new JTextField();
        jlProduktliste = new JLabel("Produktliste:");
        jtfProduktliste = new JTextField();

        jpCenter.add(jlBezeichnung);
        jpCenter.add(jtfBezeichnung);
        jpCenter.add(jlBeschreibung);
        jpCenter.add(jtfBeschreibung);
        jpCenter.add(jlArt);
        jpCenter.add(jcArt);
        jpCenter.add(jlBetrag);
        jpCenter.add(jtfBetrag);
        jpCenter.add(jlKategorie);
        jpCenter.add(jcKategorie);
        jpCenter.add(jlDatum);
        jpCenter.add(jtfDatum);
        jpCenter.add(jlProduktliste);
        jpCenter.add(jtfProduktliste);

        jfEingebenFrame.add(jpCenter, BorderLayout.CENTER);
    }

    private void buildButtons() {
        jpSouth = new JPanel(new FlowLayout());

        JButton jbAnlegen = new JButton("Anlegen");
        JButton jbAbbrechen = new JButton("Abbrechen");

        jbAnlegen.addActionListener(e -> {
            try {
                fireEvent(new GUIEvent("Anlegen", this));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        jbAbbrechen.addActionListener(e -> {
            try {
                fireEvent(new GUIEvent("Abbrechen", this));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        jpSouth.add(jbAnlegen);
        jpSouth.add(jbAbbrechen);

        jfEingebenFrame.add(jpSouth, BorderLayout.SOUTH);
    }

    private boolean checkIfAllFilled() {
        return true;
    }

    @Override
    public void fireEvent(GUIEvent event) throws Exception  {
        if(event.getMessage() == "Anlegen") {
            EingebenAdapter.anlegen();
        }

        if(event.getMessage() == "Abbrechen" || checkIfAllFilled()) {
            // Neu-Anlegen-Fenster schließen
            jfEingebenFrame.dispose();

            // GUIController triggern
            listeners.forEach(listener -> listener.eventFired(event));
        }
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
