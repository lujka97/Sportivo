package com.example.sportivo.start_screen;

import android.content.Context;

import com.example.sportivo.Models.Reservation;
import com.example.sportivo.Services.ReservationService;

import java.util.ArrayList;

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

