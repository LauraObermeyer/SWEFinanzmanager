package main.applicationCode;

import main.app.StartApplikation;
import main.model.Eintrag;
import main.util.CSVReader;
import main.util.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EintraegeDetailansicht {

    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String ausgabenFile;
    private String einnahmenFile;
    private List<String[]> dateiInhalt, neuerDateiInhalt;
    // Header für Datei
    private final String[] header = Eintrag.getAlleAttributnamenFürFile();

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

        // Das zu löschende Exponat suchen und löschen
        for(int i = 0; i < dateiInhalt.size(); i++){
            if(dateiInhalt.get(i)[0].equals(eintrag.getBezeichnung())) {
                neuerDateiInhalt.remove(i);
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
