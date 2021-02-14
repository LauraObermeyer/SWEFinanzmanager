package main.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import main.app.StartApplikation;
import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;
import main.gui.Ausgaben.AusgabenAnzeigenGUI;
import main.gui.Ausgaben.AusgabenDetailansichtGUI;
import main.model.Benutzer;
import main.model.Eintrag;

public class UebersichtsGUI  implements IGUIEventSender {

    private ArrayList<IGUIEventListener> listeners = new ArrayList<>();

    private AusgabenAnzeigenGUI ausgabenAnzeigenGUI;
    private AusgabenDetailansichtGUI ausgabenDetailansichtGUI;

    private JFrame jfMainFrame;
    private JPanel jpHeader;

    private JTabbedPane jtbTabbedPane;
    private JPanel jpTestPanel;

    private Benutzer benutzer;

    public UebersichtsGUI(Benutzer benutzer) {
        this.benutzer = benutzer;
        ausgabenAnzeigenGUI = StartApplikation.buildAusgabenAnzeigenGUI();

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

        jpHeader.add(new JLabel("Hallo, ich bin ein Header"));

        jfMainFrame.add(jpHeader, BorderLayout.NORTH);
    }

    private void buildTabbedPane() {
        jpTestPanel = new JPanel();
        jpTestPanel.setLayout(new BorderLayout());

        /* Erzeugung eines JTabbedPane-Objektes
           JTabbedPane.TOP -> Tabs sind oberhalb der TabInhalte gesetzt
           JTabbedPane.SCROLL_TAB_LAYOUT -> Wenn zu viele Tabs vorhanden sind, dass nicht alle angezeigt werden können, wird automatisch eine Scrollbar eingefügt */
        jtbTabbedPane = new JTabbedPane (JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        // Panels hinzufügen
        jtbTabbedPane.addTab("Ausgaben", ausgabenAnzeigenGUI);

        //TODO: rausnehmen:
        jtbTabbedPane.addTab("Einnahmen", new main.gui.EinnahmenAnzeigenGUI());

        jfMainFrame.getContentPane().add(jtbTabbedPane, BorderLayout.CENTER);
    }

    public void buildDetailansichtGUI(){
        ausgabenDetailansichtGUI = StartApplikation.buildAusgabenDetailansichtGUI(ausgabenAnzeigenGUI.getClickedEintrag());
        jtbTabbedPane.setComponentAt(0, ausgabenDetailansichtGUI);
    }

    public AusgabenDetailansichtGUI getAusgabenDetailansichtGUI() {
        return ausgabenDetailansichtGUI;
    }

    public void refreshUebersichtsGUI(){
        ausgabenAnzeigenGUI = StartApplikation.buildAusgabenAnzeigenGUI();
        jtbTabbedPane.setComponentAt(0, ausgabenAnzeigenGUI);
    }

    @Override
    public void fireEvent(GUIEvent event) {
        listeners.forEach( listeners -> listeners.eventFired(event));
    }

    @Override
    public void addListener(IGUIEventListener listener) {
        listeners.add(listener);
        ausgabenAnzeigenGUI.addListener(listener);
    }

    @Override
    public void removeListener(IGUIEventListener listener) {
        listeners.add(listener);
        ausgabenAnzeigenGUI.removeListener(listener);
    }
}
