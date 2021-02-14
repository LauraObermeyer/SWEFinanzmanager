package main.gui.Ausgaben;

import main.event.GUIEvent;
import main.event.IGUIEventListener;
import main.event.IGUIEventSender;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class EingebenGUI implements IGUIEventSender {

    private JFrame jfEingebenFrame;

    //Events
    private ArrayList<IGUIEventListener> listeners = new ArrayList<IGUIEventListener>();

    public EingebenGUI() {
        jfEingebenFrame = new JFrame(this.getClass().getSimpleName());
        jfEingebenFrame.setTitle("Neuen Eintrag anlegen");
        jfEingebenFrame.setLayout(new BorderLayout(5,5));
        jfEingebenFrame.setVisible(true);
        jfEingebenFrame.setSize(500, 650);
        jfEingebenFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void fireEvent(GUIEvent event) throws Exception {


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
