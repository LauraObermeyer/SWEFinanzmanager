package main.applicationCode;

import java.util.ArrayList;

public class ArrayListInStringArray {

    // Methode, um ArrayList<String> in String[] umzuwandeln
    public static String[] getStringArray(ArrayList<String> arr) {
        // String Array deklarieren und initalisieren
        String str[] = new String[arr.size()];

        // ArrayList zu Array Umwandlung
        for (int j = 0; j < arr.size(); j++) {
            // Jeden Wert zum String-Array hinzuweisen
            str[j] = arr.get(j);
        }

        return str;
    }
}
