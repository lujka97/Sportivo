package com.example.sportivo.start_screen;

import android.content.Context;

import com.example.sportivo.Models.Sport;
import com.example.sportivo.Services.SportService;
import com.example.sportivo.admin_screen.AdminReservationsDataStorage;

import java.util.ArrayList;

public class SportsListFragmentDataStorage {

        public static ArrayList<Sport> sports = new ArrayList<Sport>();

        public static void fillData(final Context context, final SportsListFragmentAdapter adapter, boolean isAdmin) {

            String url;
            if(isAdmin){
                SportService.setSportsForCompany(context, adapter, AdminReservationsDataStorage.companyId);
            }else {
                SportService.setSports(context, adapter);
            }

        }

        public static Sport getSportById(int id){
            for (Sport sport : sports){
                if(sport.getId() == id){
                    return sport;
                }
            }
            return null;
        }
    }

