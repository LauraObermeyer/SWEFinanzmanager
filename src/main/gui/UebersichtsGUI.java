package main.gui;

import javax.swing.*;
import java.awt.*;
import main.gui.Ausgaben.AusgabenAnzeigenGUI;
import main.gui.EinnahmenAnzeigenGUI;
import main.model.Benutzer;

public class UebersichtsGUI {

    private JFrame jfMainFrame;
    private JPanel jpHeader;

    private JTabbedPane jtbTabbedPane;
    private JPanel jpTestPanel;

    private Benutzer benutzer;

    public UebersichtsGUI(Benutzer benutzer) {
        this.benutzer = benutzer;

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
        jtbTabbedPane.addTab("Ausgaben", new AusgabenAnzeigenGUI(benutzer));

        jtbTabbedPane.addTab("Einnahmen", new EinnahmenAnzeigenGUI());

        jfMainFrame.getContentPane().add(jtbTabbedPane, BorderLayout.CENTER);
    }

    public void addListener(GUIController guiController) {
    }

}
