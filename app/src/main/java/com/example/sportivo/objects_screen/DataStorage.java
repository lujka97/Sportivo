package com.example.sportivo.objects_screen;

import java.util.HashMap;

public class DataStorage {
    public static HashMap<Integer, Objects> listViewData = new HashMap<Integer, Objects>();

    public static void fillData() {
        for (int i = 0; i < cageball.length; i++) {
            Objects aObject = new Objects(i + 1, cageball[i]);
            listViewData.put(i, aObject);
        }
    }

    private static String[] cageball = {"Športski Centar Mačak", "Cageball Winner", "SRC Kopilica", "Cageball Penal"};
}
