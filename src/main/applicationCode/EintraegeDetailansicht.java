package main.applicationCode;

import main.app.StartApplikation;
import main.model.Eintrag;
import main.model.EintragRepository;
import main.util.CSVReader;
import main.util.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EintraegeDetailansicht {

    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String ausgabenFile;
    private String einnahmenFile;
    private List<String[]> dateiInhalt, neuerDateiInhalt;
    // Header für Datei
    private final String[] header = Eintrag.getAlleAttributnamenFürFile();
    private EintragRepository eintragVerwaltung;

    public EintraegeDetailansicht(EintragRepository eintragVerwaltung){
        this.eintragVerwaltung = eintragVerwaltung;
    }

    public void deleteEintrag(Eintrag eintrag){
        ausgabenFile = StartApplikation.ausgabenFileNameErstellen();
        einnahmenFile = StartApplikation.einnahmenFileNameErstellen();

        if(eintrag.getArt().toString() == "Ausgabe"){
            csvWriter = new CSVWriter(ausgabenFile, true);
            csvReader = new CSVReader(ausgabenFile);
        } else {
            csvWriter = new CSVWriter(einnahmenFile, true);
            csvReader = new CSVReader(einnahmenFile);
        }
        // Liste für Inhalt der Datei
        dateiInhalt = new ArrayList<>();
        neuerDateiInhalt = new ArrayList<>();

        // Bisherigen Inhalt aus der Datei auslesen
        try {
            dateiInhalt = csvReader.readData();
            neuerDateiInhalt = csvReader.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.eintragVerwaltung.entferne(eintrag);

        // Den zu löschenden Eintrag suchen und löschen
        for(int i = 0; i < dateiInhalt.size(); i++){
            if(UUID.fromString(dateiInhalt.get(i)[0]).compareTo(eintrag.getId())==0){
                neuerDateiInhalt.remove(i);
                break;
            }
        }

        // Datei mit neuem Inhalt füllen, der alles bis auf die Zeile des zu löschenden Eintrags enthält
        try {
            csvWriter.writeDataToFile(neuerDateiInhalt, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
