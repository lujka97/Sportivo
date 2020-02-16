package com.example.sportivo.start_screen;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.R;
import com.example.sportivo.Models.Reservation;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.Services.Singleton;
import com.example.sportivo.Services.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationsListFragmentDataStorage {

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public static void fillData(Context context, ReservationsListFragmentAdapter adapter) {

        ReservationService.setReservations(context, adapter);
    }

    public static Reservation getReservationById(int id){
        for (Reservation reservation : reservations){
            if(reservation.getReservationId() == id){
                return reservation;
            }
        }
        return null;
    }

    public static void removeReservation(int id){
        Reservation reservation = null;
        for (Reservation res : reservations){
            if(res.getReservationId() == id){
                reservation = res;
            }
        }
        if(reservation != null) {
            reservations.remove(reservation);
        }
    }
}

