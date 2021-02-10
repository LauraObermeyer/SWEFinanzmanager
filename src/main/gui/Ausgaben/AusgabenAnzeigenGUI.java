package main.gui.Ausgaben;

import main.adapter.AusgabenAnzeigenAdapter;
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

public class AusgabenAnzeigenGUI extends JPanel implements IGUIEventSender {

    // Benutzer
    private Benutzer benutzer;

    // Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    // Ausgaben-Tabelle
    private JTable jtAusgaben;
    private JPanel jpTabelle;

    // Liste aller Exponate (die werden in der Tabelle angezeigt)
    List<Eintrag> ausgaben;

    // Suche
    private JTextField jtfSearchbar;
    private TableModel model;
    private TableRowSorter sorter;

    // Eintrag, der bei Klick auf die entsprechende Zeile in der Tabelle der DetailansichtGUI übergeben wird
    private Eintrag clickedEintrag;

    public AusgabenAnzeigenGUI(Benutzer benutzer) {
        this.benutzer = benutzer;
        AusgabenAnzeigenAdapter.buildFileName(benutzer);

        this.setLayout(new BorderLayout());
        buildTabelle();
    }

    private void buildTabelle() {
        jpTabelle = new JPanel(new BorderLayout());

        String data[][] = AusgabenAnzeigenAdapter.getAusgabenListe();

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
            clickedEintrag = AusgabenAnzeigenAdapter.getAusgaben().get(jtAusgaben.getSelectedRow());
            fireEvent(new GUIEvent("DetailansichtOeffnen", this));
        });

        // Scrollpane mit Tabelle jtExponate zum Panel jpCenter hinzufügen
        jpTabelle.add(new JScrollPane(jtAusgaben), BorderLayout.CENTER);
        this.add(jpTabelle, BorderLayout.CENTER);
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
