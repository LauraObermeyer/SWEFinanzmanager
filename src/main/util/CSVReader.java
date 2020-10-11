package main.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static final char DEFAULT_DELIMITER = ';';
    public static final char DEFAULT_COMMENT = '#';

    private String csvFileName = null;
    private File csvFile = null;

    private CSVReader() {
    }

    // CSVReader für ein gegebenes File erstellen
    public CSVReader( String csvFileName ) throws IllegalArgumentException {
        this.csvFileName = csvFileName;
        this.csvFile = this.checkFile( this.csvFileName );
    }

    // Existenz des Files checken
    private final File checkFile(String fileName) throws IllegalArgumentException{
        if(fileName == null || fileName.length() == 0) throw new IllegalArgumentException( "File name must be given!" );
        File csvFile = new File( fileName );
        if( !csvFile.exists()) throw new IllegalArgumentException( "File does not exist" );

        return csvFile;
    }

    // Daten aus dem gegebenen File auslesen
    public final List<String[]> readData() throws IOException {
        return this.readData( 0, DEFAULT_DELIMITER, DEFAULT_COMMENT );
    }

    // Daten aus dem gegebenen File auslesen
    public final List<String[]> readData( int expectedColumns, char delimiter, char comment ) throws IOException {
        List<String[]> allLines = new ArrayList<String[]>();

        BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( this.csvFile ), "UTF8" ) );
        try{
            String line = "";
            String[] lineElements = null;
            int numLinesRead = 0;

            while( line != null ){
                line = reader.readLine();
                numLinesRead++;
                if( line == null ) break;

                if( line.startsWith( ""+comment ) || line.length() == 0 ){
                    continue;
                }

                lineElements = line.split( ""+delimiter );
                if( expectedColumns > 0  && expectedColumns != lineElements.length ) {
                    System.out.print( "csv-reader, file line number " + numLinesRead
                            + ": different number of columns (exp: " + expectedColumns
                            + ", current: " + lineElements.length + ")!");

                    String[] sArr = new String[ expectedColumns ];
                    if( expectedColumns <= lineElements.length ) {

                        for( int i = 0 ; i < expectedColumns ; i++ ){
                            sArr[i] = lineElements[i] == null ? "" : lineElements[i];
                        }
                    }
                    else if( expectedColumns > lineElements.length ) {
                        System.arraycopy( lineElements, 0, sArr, 0, lineElements.length );
                        for( int i = lineElements.length ; i < expectedColumns ; i++ ){
                            sArr[i] = "";
                        }
                    }
                    lineElements = sArr;
                    System.out.println( " -> corrected to " + expectedColumns );
                }
                allLines.add( lineElements );
            }
        }
        catch( IOException e ){
            System.out.println(e.getMessage());
            throw e;
        }
        finally {
            reader.close();
        }

        return allLines;
    }
}