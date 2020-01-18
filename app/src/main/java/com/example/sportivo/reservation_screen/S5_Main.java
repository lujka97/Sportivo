package com.example.sportivo.reservation_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.Singleton;
import com.example.sportivo.TimeSlot;
import com.example.sportivo.time_screen.Time;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class S5_Main extends AppCompatActivity {

    TextView reservationDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rs_termin_base);

        reservationDate = (TextView) findViewById(R.id.reservationDate);
        //reservationDate.setText(new SimpleDateFormat("dd MMM yyyy").format(new Date()));
        setDate();
        reservationDate.setText(SimpleDateFormat.getDateInstance().format(new Date()));

        reservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Time.class);
                startActivity(intent);
            }
        });
    }

    private void setDate(){
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        ReservationDataStorage.year = cal.get(Calendar.YEAR);
        //Months are counted form 0
        ReservationDataStorage.month = cal.get(Calendar.MONTH) + 1;
        ReservationDataStorage.day = cal.get(Calendar.DAY_OF_MONTH);

        ReservationDataStorage.url = getApplicationContext().getString(R.string.baseURL) + getApplicationContext().getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    private void setDate(int year, int month, int day){

    }

    @Override
    protected void onResume() {
        super.onResume();


        JsonArrayRequest getReservations = new JsonArrayRequest(Request.Method.GET, ReservationDataStorage.url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("blabla", "response: " + response.toString());

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type type = new TypeToken<List<Reservation>>(){}.getType();
                ReservationDataStorage.reservations = gson.fromJson(response.toString(), type);
                
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(getReservations);
    }
    
    private ArrayList<TimeSlot> getAvailableSlots(int sportId, int companyId, int length){
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        ArrayList<TimeSlot> reservedSlots = new ArrayList<>();

        reservedSlots = getReservedSlots();

            
        return availableSlots;
    }
    
    private ArrayList<TimeSlot> getReservedSlots(){
        final ArrayList<TimeSlot> reserved = new ArrayList<>();

        for (Reservation reservation : ReservationDataStorage.reservations){
            reserved.add(new TimeSlot(reservation.getStartTime(), reservation.getEndTime()));
        }

        return reserved;
    }
}
