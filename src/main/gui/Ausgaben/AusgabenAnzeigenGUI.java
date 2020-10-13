package main.gui.Ausgaben;

import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.model.Art;
import main.model.Eintrag;
import main.model.Kategorie;
import main.util.CSVReader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AusgabenAnzeigenGUI extends JPanel implements IGUIEventSender {

    // Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    // Ausgaben-Tabelle
    private JTable jtAusgaben;
    private JPanel jpTabelle;

    // Liste aller Exponate (die werden in der Tabelle angezeigt)
    List<Eintrag> ausgaben;

    private CSVReader csvReader;
    private String ausgabenFile = "resources/ausgaben.csv";

    // Suche
    private JTextField jtfSearchbar;
    private TableModel model;
    private TableRowSorter sorter;

    // Eintrag, der bei Klick auf die entsprechende Zeile in der Tabelle der DetailansichtGUI übergeben wird
    private Eintrag clickedEintrag;

    public AusgabenAnzeigenGUI() {
        this.setLayout(new BorderLayout());
        buildExponatTabelle();
    }

    private void buildExponatTabelle() {
        jpTabelle = new JPanel(new BorderLayout());

        // csvReader initialisieren
        csvReader = new CSVReader(ausgabenFile);
        // Liste für Inhalt der Ausgaben-Datei
        List<String[]> dateiInhalt = new ArrayList<>();

        // Exponate aus entsprechender csv-Datei auslesen
        try {
            dateiInhalt = csvReader.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Basierend auf Text aus Ausgaben-Datei die entsprechenden Einträge generieren (zeilenweise) und zur Liste "ausgaben" hinzufügen
        ausgaben = new ArrayList<>();
        for (int i = 0; i < dateiInhalt.size(); i++) {
            // Tabelleneinträge mit zu wenig Attributen und Eingaben überspringen
            if (dateiInhalt.get(i).length == 7 && dateiInhalt.get(i)[2].equals("Ausgabe")) {
                try {
                    String[] z = dateiInhalt.get(i); // Inhalt der aktuellen Zeile z
                    Kategorie kategorie = new Kategorie(z[3]);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                    Date datum = formatter.parse(z[4]);
                    ausgaben.add(new Eintrag(z[0], z[1], Art.valueOf("Ausgabe"), kategorie, datum, z[5]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Tabelleninhalt aufbauen
        String data[][];
        if(ausgaben.size() > 0) {
            data = new String[ausgaben.size()][10];
        } else{
            data = new String[1][10];
        }
        // Für jede Ausgabe aus "ausgaben" alle Attribute zu "data" hinzufügen
        for(int i = 0; i < dateiInhalt.size(); i++){
            //TODO: Wenn Eingaben hinzu kommen buggt das hier wahrscheinlich
            data[i] = dateiInhalt.get(i);
            // Letzte Tabellenzeile soll andeuten, dass man auf Tabellenzeile klicken kann, um zu entsprechender Detailansicht zu gelangen
            //data[i][5] = ">";
        }

        // Spaltennamen holen
        String column[] = Eintrag.getAlleAttributnamenFürTabelle();

        // Tabelle mit Model mit Daten (data) und Spaltennamen (column) aufbauen
        model = new DefaultTableModel(data, column);
        sorter = new TableRowSorter<>(model); // Nötig für Suche
        jtAusgaben = new JTable(model);
        jtAusgaben.setRowSorter(sorter); // Nötig für Suche

        // ">" in der letzten Spalte zentrieren, um anzudeuten, dass man auf die jeweiligen Zeilen klicken kann
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jtAusgaben.getColumnModel().getColumn(7).setCellRenderer( centerRenderer );

        // Action Listener für Tabelle hinzufügen, damit Zeilen klickbar sind, um zur jeweiligen Exponat-Detailansicht zu gelangen
        jtAusgaben.getSelectionModel().addListSelectionListener(e -> {
            clickedEintrag = ausgaben.get(jtAusgaben.getSelectedRow());
            fireEvent(new GUIEvent("Exponat-Detailansicht-Öffnen", this));
        });

        // Scrollpane mit Tabelle jtExponate zum Panel jpCenter hinzufügen
        jpTabelle.add(new JScrollPane(jtAusgaben), BorderLayout.CENTER);
        this.add(jpTabelle, BorderLayout.CENTER);
    }

    @Override
    public void fireEvent(GUIEvent event) {
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
