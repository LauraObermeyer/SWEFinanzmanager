package main.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BenutzerAnlegenGUI {

    private JFrame jfBenutzerAnlegen;

    public BenutzerAnlegenGUI() throws IOException {
        jfBenutzerAnlegen = new JFrame("Neuen Benutzer anlegen");
        jfBenutzerAnlegen.setLayout(new BorderLayout(5,5));
        jfBenutzerAnlegen.setVisible(true);
        jfBenutzerAnlegen.setSize(500, 650);
        jfBenutzerAnlegen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
