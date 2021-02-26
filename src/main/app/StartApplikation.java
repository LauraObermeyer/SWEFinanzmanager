package main.app;

import main.gui.Eintraege.EintraegeAnzeigenGUI;
import main.gui.Eintraege.EintraegeDetailansichtGUI;
import main.gui.Eintraege.EingebenGUI;
import main.gui.BenutzerAnlegenGUI;
import main.gui.GUIController;
import main.gui.UebersichtsGUI;
import main.model.Benutzer;
import main.model.EMail;
import main.model.Eintrag;
import main.util.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StartApplikation {

    // GUI Komponenten
    private static UebersichtsGUI uebersichtsGUI;
    private static BenutzerAnlegenGUI benutzerAnlegenGUI;
    private static EintraegeAnzeigenGUI eintraegeAnzeigenGUI;
    private static EintraegeDetailansichtGUI eintraegeDetailansichtGUI;

    // CSV Reader und Files
    private static CSVReader csvReaderFürBenutzerFile;
    private static final String benutzerFile = "./resources/benutzer.csv";
    private static String ausgabenFile;
    private static String einnahmenFile;

    // Benutzer des Finanzmanagers
    private static Benutzer benutzer;

    public static void main( String[] args ) throws Exception {
        startGuiBestimmenUndAufrufen();
    }

    private static void startGuiBestimmenUndAufrufen() throws Exception {
        if(nutzerIstNeu() == true){
            benutzerAnlegenGuiAufrufen();
        } else {
            uebersichtsGuiAufrufen();
        }
    }

    private static boolean nutzerIstNeu() {
        List<String[]> dateiInhalt = getBenutzerDateiinhalt();
        if(dateiInhalt.size() > 0) {
            benutzerObjektInitialisieren(dateiInhalt);
            return false;
        }
        return true;
    }

    private static List<String[]> getBenutzerDateiinhalt(){
        csvReaderFürBenutzerFile = new CSVReader(benutzerFile);
        List<String[]> dateiInhaltDesBenutzerFiles = new ArrayList<>();
        try {
            dateiInhaltDesBenutzerFiles = csvReaderFürBenutzerFile.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dateiInhaltDesBenutzerFiles;
    }

    private static void benutzerAnlegenGuiAufrufen() throws Exception {
        benutzerAnlegenGUI = new BenutzerAnlegenGUI();
        new GUIController(benutzerAnlegenGUI);
    }

    private static void uebersichtsGuiAufrufen() throws Exception {
        buildUebersichtsGUI();
        new GUIController(uebersichtsGUI);
    }

    private static void benutzerObjektInitialisieren(List<String[]> dateiInhaltDesBenutzerFiles) {
        benutzer = new Benutzer(dateiInhaltDesBenutzerFiles.get(0)[0], dateiInhaltDesBenutzerFiles.get(0)[1], emailDesBenutzersErzeugenAus(dateiInhaltDesBenutzerFiles.get(0)[2]));
    }

    private static EMail emailDesBenutzersErzeugenAus(String emailString){
        String[] emailAdresseAufgeteiltInLokalUndDomänenteil
                = emailString.split("@");
        String[] domänenteilAufgeteiltInHostnameUndTopLevelDomain
                = emailAdresseAufgeteiltInLokalUndDomänenteil[1].split("\\.");
        EMail fertigeEmailAdresse = new EMail
                (emailAdresseAufgeteiltInLokalUndDomänenteil[0],
                domänenteilAufgeteiltInHostnameUndTopLevelDomain[0],
                domänenteilAufgeteiltInHostnameUndTopLevelDomain[1]);
        return fertigeEmailAdresse;
    }

    public static void buildUebersichtsGUI() {
        benutzerObjektInitialisieren(getBenutzerDateiinhalt());
        try{
            uebersichtsGUI = new UebersichtsGUI(benutzer);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static UebersichtsGUI buildUebersichtsGUIfromController() {
        buildUebersichtsGUI();
        return uebersichtsGUI;
    }

    public static EintraegeAnzeigenGUI buildEintraegeAnzeigenGUI(){
        eintraegeAnzeigenGUI = new EintraegeAnzeigenGUI(benutzer);
        return eintraegeAnzeigenGUI;
    }

    public static EintraegeDetailansichtGUI buildEintraegeDetailansichtGuiVon(Eintrag eintrag){
        eintraegeDetailansichtGUI = new EintraegeDetailansichtGUI(eintrag);
        return eintraegeDetailansichtGUI;
    }

    public static EingebenGUI buildEingebenGUI(boolean neuAnlegen, Optional<Eintrag> eintrag) {
        return new EingebenGUI(neuAnlegen, eintrag);
    }

    public static String ausgabenFileNameErstellen() {
        String ausgabenFilePrefix = "resources/ausgaben";
        ausgabenFile = ausgabenFilePrefix + benutzer.getVorname() + benutzer.getNachname() + ".csv";
        return ausgabenFile;
    }

    public static String einnahmenFileNameErstellen() {
        String einnahmenFilePrefix = "resources/einnahmen";
        einnahmenFile = einnahmenFilePrefix + benutzer.getVorname() + benutzer.getNachname() + ".csv";
        return einnahmenFile;
    }
}