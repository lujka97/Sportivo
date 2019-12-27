package com.example.sportivo.start_screen;

import java.util.HashMap;

public class Frag1_DataStorage {

        public static HashMap<Integer, Frag1_Sports> listViewData = new HashMap<Integer, Frag1_Sports>();

        public static void fillData() {
            for (int i = 0; i < names.length; i++) {

                Frag1_Sports aFrag1_Sports = new Frag1_Sports(i + 1, names[i]);
                listViewData.put(i, aFrag1_Sports);
            }
        }

        private static String[] names = {"Cageball", "Tenis", "Futsal", "Stolni tenis", "Biljar", "Kuglanje"};
    }

