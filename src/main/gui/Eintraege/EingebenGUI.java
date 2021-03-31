package main.gui.Eintraege;

import main.applicationCode.Eingeben;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.model.Eintrag;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class EingebenGUI implements IGUIEventSender {

    private boolean neuAnlegen; // true, wenn neuer Eintrag angelegt werden soll; false, wenn bestehender Eintrag bearbeitet werden soll
    // Zu bearbeitender Eintrag (wird nur gesetzt, wenn EingebenGUI zum Bearbeiten und nicht zum neu anlegen genutzt wird)
    private Eintrag eintrag;
    private JFrame jfEingebenFrame;
    private JPanel jpCenter, jpSouth;
    private JLabel jlBezeichnung, jlBeschreibung, jlArt, jlBetrag, jlKategorie, jlDatum, jlProduktliste;
    private JTextField jtfBezeichnung, jtfBeschreibung, jtfProduktliste;
    private JFormattedTextField jtfBetrag, jtfDatum;
    private JComboBox<String> jcKategorie, jcArt;

    //Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    public EingebenGUI(boolean neuAnlegen, Optional<Eintrag> eintrag) {
        this.neuAnlegen = neuAnlegen;

        jfEingebenFrame = new JFrame(this.getClass().getSimpleName());
        jfEingebenFrame.setTitle("Neuen Eintrag anlegen");
        jfEingebenFrame.setLayout(new BorderLayout(5,5));
        jfEingebenFrame.setVisible(true);
        jfEingebenFrame.setSize(400, 400);
        jfEingebenFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        buildBeschreibungstext();
        buildEingabefelder();
        buildButtons();

        if (!neuAnlegen && eintrag.isPresent()) {
            this.eintrag = eintrag.get();
            textfelderVorausfuellen();
        }
    }

    private void buildBeschreibungstext() {
        JLabel jlBeschreibungstext = new JLabel("Gib die Daten des neuen Eintrags an:");
        jfEingebenFrame.add(jlBeschreibungstext, BorderLayout.NORTH);
    }

    private void buildEingabefelder() {
        jpCenter = new JPanel(new GridLayout(7, 2));

        //Formatter, damit in Betrags-Feld nur Double Zahlen eingetragen werden können
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatterDouble = new NumberFormatter(format);
        formatterDouble.setValueClass(Double.class);
        formatterDouble.setAllowsInvalid(false);
        //Formatter für Datumsfeld
        SimpleDateFormat formatterDatum = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        jlBezeichnung = new JLabel("Bezeichnung:");
        jtfBezeichnung = new JTextField();
        jlBeschreibung = new JLabel("Beschreibung:");
        jtfBeschreibung = new JTextField();
        jlArt = new JLabel("Art:");
        String[] art = { "Einnahme", "Ausgabe"};
        jcArt = new JComboBox<String>(art);
        jlBetrag = new JLabel("Betrag:");
        jtfBetrag = new JFormattedTextField(formatterDouble);
        //jtfBetrag = new JFormattedTextField(new DecimalFormat());git pusll
        jlKategorie = new JLabel("Kategorie:");
        String[] kategorien = { "Einkauf", "Kategorie 2", "Kategorie 3", "Kategorie 4", "Kategorie 5", "Kategorie 6"};
        jcKategorie = new JComboBox<String>(kategorien);
        jlDatum = new JLabel("Datum:");
        jtfDatum = new JFormattedTextField(formatterDatum);
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

    private void textfelderVorausfuellen() {
        jtfBezeichnung.setText(eintrag.getBezeichnung());
        jtfBeschreibung.setText(eintrag.getBeschreibung());
        if (eintrag.getArt().toString() == "Ausgabe"){
            jcArt.setSelectedItem("Ausgabe");
        }
        jtfBetrag.setText(Double.toString(eintrag.getBetrag()));
        // TODO: beim Bearbeiten wird das Komma beim Betrag verschoben
        // TODO: set Kategorie
        SimpleDateFormat formatterDatum = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        jtfDatum.setText(formatterDatum.format(eintrag.getDatum()));
        jtfProduktliste.setText(eintrag.getProduktliste());
    }

    private void buildButtons() {
        jpSouth = new JPanel(new FlowLayout());

        String buttonBeschriftung;
        if(neuAnlegen) {
            buttonBeschriftung = "Anlegen";
        } else {
            buttonBeschriftung = "Bearbeiten";
        }
        JButton jbAnlegen = new JButton(buttonBeschriftung);
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
        if(jtfBezeichnung.getText().isEmpty() || jtfBeschreibung.getText().isEmpty() || jtfBetrag.getText().isEmpty() || jtfDatum.getText().isEmpty() || jtfProduktliste.getText().isEmpty()){
            return false;
        }
        return true;
    }

    private String[] getTextfelderInhalt() {
        String[] textfelderInhalt = new String[7];
        textfelderInhalt[0] = jtfBezeichnung.getText();
        textfelderInhalt[1] = jtfBeschreibung.getText();
        textfelderInhalt[2] = jcArt.getSelectedItem().toString();
        textfelderInhalt[3] = jtfBetrag.getText();
        textfelderInhalt[4] = jcKategorie.getSelectedItem().toString();
        textfelderInhalt[5] = jtfDatum.getText();
        textfelderInhalt[6] = jtfProduktliste.getText();

        return textfelderInhalt;
    }

    @Override
    public void fireEvent(GUIEvent event) throws Exception  {
        if(event.getMessage() == "Anlegen") {
            if(checkIfAllFilled()){
                Eingeben eingeben = new Eingeben();
                eingeben.anlegen(getTextfelderInhalt(), neuAnlegen, eintrag);
            }
            else {
            JOptionPane.showMessageDialog(null,
                    "Es müssen alle Felder ausgefüllt werden, um einen neuen Eintrag anzulegen.",
                    "Fehlende Attribute",
                    JOptionPane.ERROR_MESSAGE);
            }
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
