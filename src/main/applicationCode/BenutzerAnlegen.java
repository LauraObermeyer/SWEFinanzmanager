package main.applicationCode;

import main.adapter.repositories.BenutzerRepository;
import main.model.Benutzer;
import main.model.EMail;
import main.util.CSVReader;
import main.util.CSVWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BenutzerAnlegen {

    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String benutzerFile = "resources/benutzer.csv";
    private List<String[]> dateiInhalt;
    private final String[] header = {"Id", "Vorname", "Nachname", "EMail"};
    private BenutzerRepository benutzerVerwaltung;

    public BenutzerAnlegen(BenutzerRepository benutzerVerwaltung) {
        this.benutzerVerwaltung = benutzerVerwaltung;
    }

    public void benutzerSpeichern(JTextField jtfVorname, JTextField jtfNachname, JTextField jtfEmail) throws Exception {
        Benutzer benutzer = new Benutzer(jtfVorname.getText(), jtfNachname.getText(), emailDesBenutzersErzeugenAus(jtfEmail.getText()));
        try {
            csvWriter = new CSVWriter(benutzerFile, true);
            dateiInhalt = Collections.singletonList(new String[]{benutzer.getId().toString(), jtfVorname.getText(), jtfNachname.getText(), jtfEmail.getText()});
            csvWriter.writeDataToFile(dateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.benutzerVerwaltung.fuegeBenutzerHinzu(benutzer);
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
}
