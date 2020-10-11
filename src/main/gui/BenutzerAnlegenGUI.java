package main.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;

public class BenutzerAnlegenGUI  implements IGUIEventSender {

    private JFrame jfBenutzerAnlegen;
    private JPanel jpButtons;
    private JPanel jpInputFields;
    private JLabel jlVorname, jlNachname, jpEmail;
    private JTextField jtfVorname, jtfNachname, jtfEmail;

    //Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    public BenutzerAnlegenGUI() throws IOException {
        jfBenutzerAnlegen = new JFrame("Benutzer anlegen");
        jfBenutzerAnlegen.setLayout(new BorderLayout(5,5));

        buildBeschreibungstext();
        buildInputFields();
        buildButtons();

        jfBenutzerAnlegen.setSize(400, 180);
        jfBenutzerAnlegen.setLocationRelativeTo(null);
        jfBenutzerAnlegen.setVisible(true);
        jfBenutzerAnlegen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildBeschreibungstext() {
        JLabel jlBeschreibungstext = new JLabel("Gib die Daten deines neuen Nutzers an:");

        jfBenutzerAnlegen.add(jlBeschreibungstext, BorderLayout.NORTH);
    }

    private void buildInputFields() {
        jpInputFields = new JPanel(new GridLayout(3, 2));

        jlVorname = new JLabel("Vorname:");
        jtfVorname = new JTextField();
        jlNachname = new JLabel("Nachname:");
        jtfNachname = new JTextField();
        jpEmail = new JLabel("E-Mail:");
        jtfEmail = new JTextField();

        jpInputFields.add(jlVorname);
        jpInputFields.add(jtfVorname);
        jpInputFields.add(jlNachname);
        jpInputFields.add(jtfNachname);
        jpInputFields.add(jpEmail);
        jpInputFields.add(jtfEmail);

        jfBenutzerAnlegen.add(jpInputFields, BorderLayout.CENTER);
    }

    private void buildButtons() {
        jpButtons = new JPanel(new FlowLayout());

        JButton jbAnlegen = new JButton("Speichern");
        JButton jbAbbrechen = new JButton("Abbrechen");

        jbAnlegen.addActionListener(e -> {
            try {
                fireEvent(new GUIEvent("Speichern", this));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        jbAbbrechen.addActionListener(e -> {
            try {
                fireEvent(new GUIEvent("Abbrechen", this));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        jpButtons.add(jbAbbrechen);
        jpButtons.add(jbAnlegen);

        jfBenutzerAnlegen.add(jpButtons, BorderLayout.SOUTH);
    }

    private void benutzerSpeichern() {
        //TODO: Benutzer speichern
    }

    @Override
    public void fireEvent(GUIEvent event) throws Exception {
        // Benutzer-Anlegen-Fenster schließen
        jfBenutzerAnlegen.dispose();

        if(event.getMessage() == "Speichern") {
            benutzerSpeichern();

            // GUIController triggern
            listeners.forEach(listener -> listener.eventFired(event));
        }

        if(event.getMessage() == "Abbrechen") {
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

    // Methode, um ArrayList<String> in String[] umzuwandeln
    public static String[] GetStringArray(ArrayList<String> arr) {
        // String Array deklarieren und initalisieren
        String str[] = new String[arr.size()];

        // ArrayList zu Array Umwandlung
        for (int j = 0; j < arr.size(); j++) {
            // Jeden Wert zum String-Array hinzuweisen
            str[j] = arr.get(j);
        }

        return str;
    }
}