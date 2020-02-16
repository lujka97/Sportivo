package com.example.sportivo.start_screen;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.R;
import com.example.sportivo.Services.Singleton;
import com.example.sportivo.Models.Sport;
import com.example.sportivo.Services.SportService;
import com.example.sportivo.admin_screen.AdminReservationsDataStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

