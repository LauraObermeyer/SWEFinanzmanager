package main.gui;

import main.adapter.UebersichtsAdapter;
import main.app.StartApplikation;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.gui.Eintraege.EintraegeAnzeigenGUI;
import main.gui.Eintraege.EintraegeDetailansichtGUI;
import main.model.Benutzer;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class UebersichtsGUI  implements IGUIEventSender {

    private ArrayList<IGUIEventListener> listeners = new ArrayList<>();

    private EintraegeAnzeigenGUI eintraegeAnzeigenGUI;
    private EintraegeDetailansichtGUI eintraegeDetailansichtGUI;

    private JFrame jfMainFrame;
    private JPanel jpHeader;
    private JTextField jtfSearchbar;
    private TableRowSorter sorter;

    private JTextArea jtaTextArea;
    private final static Font headerFont = new Font( "SansSerif", Font.ITALIC, 30 );

    private JTabbedPane jtbTabbedPane;
    private JPanel jpTestPanel;

    private Benutzer benutzer;

    public UebersichtsGUI(Benutzer benutzer) {
        this.benutzer = benutzer;
        eintraegeAnzeigenGUI = StartApplikation.buildEintraegeAnzeigenGUI();

        buildMainFrame();
        buildHeader();
        buildTabbedPane();

        jfMainFrame.setVisible(true);
    }

    private void buildMainFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jfMainFrame = new JFrame();
        jfMainFrame.setTitle(benutzer.getTitle());
        jfMainFrame.setSize(screenSize.width, screenSize.height);
        jfMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jfMainFrame.setResizable(true);
        jfMainFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        jfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfMainFrame.setLayout(new BorderLayout(5,5));
    }

    private void buildHeader() {
        jpHeader = new JPanel();

        jtaTextArea = new JTextArea("Finanzverwaltung");
        jtaTextArea.setFont(headerFont);
        jtaTextArea.setEditable(false);
        jtaTextArea.setBackground(jpHeader.getBackground());
        jpHeader.add(jtaTextArea);

        jfMainFrame.add(jpHeader, BorderLayout.NORTH);
    }

    private void buildTabbedPane() {
        jpTestPanel = new JPanel();
        jpTestPanel.setLayout(new BorderLayout());

        jtbTabbedPane = new JTabbedPane (JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        jtbTabbedPane.addTab("Einträge", eintraegeAnzeigenGUI);
        jtbTabbedPane.addTab("Statistiken", new main.gui.Statistiken.StatistikenAnzeigenGUI());

        jfMainFrame.getContentPane().add(jtbTabbedPane, BorderLayout.CENTER);
    }

    public void buildDetailansichtGUI(){
        UebersichtsAdapter uebersichtsAdapter = new UebersichtsAdapter();
        eintraegeDetailansichtGUI = uebersichtsAdapter.buildDetailansichtGUI(eintraegeAnzeigenGUI);
        jtbTabbedPane.setComponentAt(0, eintraegeDetailansichtGUI);
    }

    public EintraegeDetailansichtGUI getAusgabenDetailansichtGUI() {
        return eintraegeDetailansichtGUI;
    }

    public void refreshUebersichtsGUI(){
        UebersichtsAdapter uebersichtsAdapter = new UebersichtsAdapter();
        eintraegeAnzeigenGUI = uebersichtsAdapter.refreshUebersichtsGUI();
        jtbTabbedPane.setComponentAt(0, eintraegeAnzeigenGUI);
    }

    @Override
    public void fireEvent(GUIEvent event) {
        listeners.forEach( listeners -> listeners.eventFired(event));
    }

    @Override
    public void addListener(IGUIEventListener listener) {
        listeners.add(listener);
        eintraegeAnzeigenGUI.addListener(listener);
    }

    @Override
    public void removeListener(IGUIEventListener listener) {
        listeners.add(listener);
        eintraegeAnzeigenGUI.removeListener(listener);
    }
}
