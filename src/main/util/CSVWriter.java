package main.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVWriter {

    public static final char DEFAULT_DELIMITER = ';';
    public static final char DEFAULT_COMMENT = '#';

    private String csvFileName = null;
    private File csvFile = null;

    private CSVWriter() {
    }

    // // CSVWriter für ein gegebenes File erstellen
    public CSVWriter( String csvFileName, boolean createIfNotExists ) throws IllegalArgumentException {
        this.csvFileName = csvFileName;
        this.csvFile = this.checkFile( this.csvFileName, createIfNotExists );
    }

    private final File checkFile(String fileName, boolean create) throws IllegalArgumentException{
        if(fileName == null || fileName.length() == 0) throw new IllegalArgumentException( "File name must be given!" );
        File csvFile = new File( fileName );
        if( !csvFile.exists() ) {
            if( create ) {
                try {
                    csvFile.createNewFile();
                } catch (IOException e) {
                    throw new IllegalArgumentException( "File could not be created: " + e.getMessage() );
                }
            }
            else {
                throw new IllegalArgumentException( "File does not exist! If it should be created automatically, use argument value create=true" );
            }
        }
        return csvFile;
    }

    // Daten in File schreiben
    public final void writeDataToFile( Object[][] data, String[] header ) throws IOException, IllegalArgumentException {
        this.writeDataToFile(data, header, DEFAULT_DELIMITER, DEFAULT_COMMENT );
    }

    // Daten in File schreiben
    public final void writeDataToFile( Object[][] data, String[] header, char delimiter, char commentChar )
            throws IOException, IllegalArgumentException {

        if( data == null ) throw new IllegalArgumentException( "Data must be given!");

        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( this.csvFile ), "UTF8" ) );
        if( data[0] == null ) {
            writer.close();
            throw new IllegalArgumentException( "first data line is not given!");
        }

        // write the header
        if( header != null  &&  header.length > 0 ) {
            try{
                writer.write( commentChar );
                int arrLen = header.length;
                for( int i = 0 ; i < arrLen ; i++ ){
                    if( header[i] != null )  // if objArr[i] == null: ignore writing (-> empty value)
                        writer.write( header[i] );
                    if( arrLen - i > 1 )
                        writer.write( delimiter );
                }
                writer.newLine();
            }
            catch( IOException e ) {
                writer.flush();
                writer.close();
                throw e;
            }
        }

        // write the data
        try{
            int arrLen = data[0].length;
            for( Object[] objArr: data ){
                for( int i = 0 ; i < arrLen ; i++ ){
                    if( objArr[i] != null )  // if objArr[i] == null: ignore writing (-> empty value)
                        writer.write( objArr[i].toString() );
                    if( arrLen - i > 1 )
                        writer.write( delimiter );
                }
                writer.newLine();
            }
        }
        catch( IOException e ) {
            throw e;
        }
        finally {
            writer.flush();
            writer.close();
        }
    }

    // Daten in File schreiben
    public final void writeDataToFile( List<String[]> data, String[] header ) throws IOException, IllegalArgumentException {
        this.writeDataToFile(data, header, DEFAULT_DELIMITER, DEFAULT_COMMENT );
    }

    // Daten in File schreiben
    public final void writeDataToFile( List<String[]> data, String[] header, char delimiter, char commentChar )
            throws IOException, IllegalArgumentException {

        if( data == null ) throw new IllegalArgumentException( "Data must be given!");

        try(BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( this.csvFile ), "UTF8" ) )) {

            // write the header
            if (header != null && header.length > 0) {
                try {
                    writer.write(commentChar);
                    int arrLen = header.length;
                    for (int i = 0; i < arrLen; i++) {
                        if (header[i] != null)  // if objArr[i] == null: ignore writing (-> empty value)
                            writer.write(header[i]);
                        if (arrLen - i > 1)
                            writer.write(delimiter);
                    }
                    writer.newLine();
                } catch (IOException e) {
                    writer.flush();
                    writer.close();
                    throw e;
                }
            }

            if (data.size() == 0) {
                writer.write("");
                writer.close();
            } else if (data.get(0) == null) {
                writer.close();
                throw new IllegalArgumentException("first data line is not given!");
            } else {
                int arrLen = data.get(0).length;
                data.forEach(e -> {
                    try {
                        for (int i = 0; i < arrLen; i++) {
                            if (e[i] != null)  // if objArr[i] == null: ignore writing (-> empty value)
                                writer.write(e[i].toString());
                            if (arrLen - i > 1)
                                writer.write(delimiter);
                        }
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}