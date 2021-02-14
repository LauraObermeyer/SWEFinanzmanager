package main.gui.Eintraege;

import main.adapter.EintraegeAnzeigenAdapter;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.model.Benutzer;
import main.model.Eintrag;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EintraegeAnzeigenGUI extends JPanel implements IGUIEventSender {

    // Benutzer
    private Benutzer benutzer;

    // Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    // Ausgaben-Tabelle
    private JTable jtAusgaben;
    private JPanel jpTabelle;

    // Liste aller Einträge (die werden in der Tabelle angezeigt)
    List<Eintrag> ausgaben;

    // Suche
    private JTextField jtfSearchbar;
    private TableModel model;
    private TableRowSorter sorter;

    // Eintrag, der bei Klick auf die entsprechende Zeile in der Tabelle der DetailansichtGUI übergeben wird
    private Eintrag clickedEintrag;

    public EintraegeAnzeigenGUI(Benutzer benutzer) {
        this.benutzer = benutzer;
        EintraegeAnzeigenAdapter.buildFileName(benutzer);

        this.setLayout(new BorderLayout());
        buildTabelle();
        buildButtons();
    }

    private void buildTabelle() {
        jpTabelle = new JPanel(new BorderLayout());

        String data[][] = EintraegeAnzeigenAdapter.getEintragListe();

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
        jtAusgaben.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

        // Action Listener für Tabelle hinzufügen, damit Zeilen klickbar sind, um zur jeweiligen Detailansicht zu gelangen
        jtAusgaben.getSelectionModel().addListSelectionListener(e -> {
            clickedEintrag = EintraegeAnzeigenAdapter.getEintraege().get(jtAusgaben.getSelectedRow());
            fireEvent(new GUIEvent("DetailansichtOeffnen", this));
        });

        // Scrollpane mit Tabelle zum Panel jpCenter hinzufügen
        jpTabelle.add(new JScrollPane(jtAusgaben), BorderLayout.CENTER);
        this.add(jpTabelle, BorderLayout.CENTER);
    }

    private void buildButtons() {
        JPanel jpSouth = new JPanel(new FlowLayout());

        // Buttons für Fußzeile erstellen
        JButton jbNeuAnlegen = new JButton("Neuen Eintrag anlegen");

        // ActionListener für Buttons hinzufügen
        jbNeuAnlegen.addActionListener(e -> fireEvent(new GUIEvent("EintragNeuAnlegen", this)));

        // Buttons zum Panel hinzufügen
        jpSouth.add(jbNeuAnlegen, BorderLayout.SOUTH);
        this.add(jpSouth, BorderLayout.SOUTH);
    }

    // Wenn auf eine Zeile der Tabelle geklickt wurde, liefert diese Methode den jeweiligen Eintrag
    public Eintrag getClickedEintrag(){
        return clickedEintrag;
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