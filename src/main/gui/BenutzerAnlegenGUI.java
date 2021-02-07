package main.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.applicationCode.BenutzerAnlegenUseCase;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.util.CSVReader;
import main.util.CSVWriter;

public class BenutzerAnlegenGUI  implements IGUIEventSender {

    private JFrame jfBenutzerAnlegen;
    private JPanel jpButtons;
    private JPanel jpInputFields;
    private JLabel jlVorname, jlNachname, jpEmail;
    private JTextField jtfVorname, jtfNachname, jtfEmail;

    //CSVReader und Writer
    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String benutzerFile = "resources/benutzer.csv";
    private List<String[]> dateiInhalt;
    private static final String[] header = {"Vorname", "Nachname", "EMail"};

    //Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    public BenutzerAnlegenGUI() throws IOException {
        buildGui();
    }

    private void buildGui() {
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

   /* private void benutzerSpeichern() {
        try {
            csvWriter = new CSVWriter(benutzerFile, true);
            dateiInhalt = Collections.singletonList(new String[]{jtfVorname.getText(), jtfNachname.getText(), jtfEmail.getText()});
            csvWriter.writeDataToFile(dateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void fireEvent(GUIEvent event) throws Exception {
        // Benutzer-Anlegen-Fenster schließen
        jfBenutzerAnlegen.dispose();

        if(event.getMessage() == "Speichern") {
            BenutzerAnlegenUseCase.benutzerSpeichern(jtfVorname, jtfNachname, jtfEmail);
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
}