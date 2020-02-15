package com.example.sportivo.reservationbyhour_screen;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.Court;
import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.Singleton;
import com.example.sportivo.TimeSlot;
import com.example.sportivo.TokenManager;
import com.example.sportivo.objects_screen.Company;
import com.example.sportivo.time_screen.DateSelect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView reservationDate;
    ExpandableListView expandableListView;
    MyExpandableListViewAdapterResByHour expandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rsh_activity_main);

        Intent intent = new Intent(getApplicationContext(), DateSelect.class);
        intent.putExtra("time", 1);
        startActivity(intent);

        reservationDate = (TextView) findViewById(R.id.reservationDateTime);
        expandableListView = (ExpandableListView) findViewById(R.id.reservationByHourExpandableListView);

        reservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DateSelect.class);
                intent.putExtra("time", 1);
                startActivity(intent);
            }
        });

        expandableListAdapter = new MyExpandableListViewAdapterResByHour(getApplicationContext());

        expandableListView.setAdapter(expandableListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Calendar cal = Calendar.getInstance();
        cal.set(ReservationDataStorage.getYear(), ReservationDataStorage.getMonth() - 1, ReservationDataStorage.getDay(),
                ReservationDataStorage.getHour(), ReservationDataStorage.getMinute(), 0);

        reservationDate.setText(SimpleDateFormat.getDateTimeInstance().format(cal.getTime()));

        Log.i("blabla", "url: " + ReservationDataStorage.urlResByHour);
        JsonArrayRequest getReservations = new JsonArrayRequest(Request.Method.GET, ReservationDataStorage.urlResByHour, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("blabla", "response: " + response.toString());

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type type = new TypeToken<List<Reservation>>(){}.getType();
                ReservationDataStorage.reservations = gson.fromJson(response.toString(), type);

                updateSlots();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("blabla", error.toString());
            }
        });
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(getReservations);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReservationDataStorage.availableReservations.clear();
    }

    private ArrayList<TimeSlot> getAllSlots(){
        ArrayList<TimeSlot> slots = new ArrayList<>();

        for (Company company : ReservationDataStorage.companies){
            for(Court court : company.getCourts()){
                if(court.getSportId() == ReservationDataStorage.sportId){
                    slots.add(new TimeSlot(ReservationDataStorage.getHour(), ReservationDataStorage.getMinute(),
                            ReservationDataStorage.getHour()+ ReservationDataStorage.getLength(), ReservationDataStorage.getMinute(), court.getCourtId(), court.getPrice()));
                }
            }
        }

        return slots;
    }

    private ArrayList<TimeSlot> getAllReserved(){
        final ArrayList<TimeSlot> reserved = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (Reservation reservation : ReservationDataStorage.reservations){
            calendar.setTime(reservation.getStartTime());
            reserved.add(new TimeSlot(calendar.get(Calendar.HOUR_OF_DAY), 0, calendar.get(Calendar.HOUR_OF_DAY) + ReservationDataStorage.getLength(),
                    0, reservation.getCourtId(), reservation.getCourt().getPrice()));
        }

        return reserved;
    }

    private Map<String, ArrayList<TimeSlot>> getAvailableSlots(){

        ArrayList<TimeSlot> availableSlots = getAllSlots();
        ArrayList<TimeSlot> reservedSlots = getAllReserved();

        for ( TimeSlot slot : reservedSlots) {
            availableSlots.remove(new TimeSlot(slot.getStartsHour(), slot.getStartsMinutes(), slot.getEndsHour(), slot.getEndsMinutes(), slot.getCourtId(), slot.getPrice()));
        }

        Map<String, ArrayList<TimeSlot>> availableSlotsMap = new HashMap<>();

        for( TimeSlot ts : availableSlots){
            int courtId = ts.getCourtId();

            int companyId = getCompanyIdFromCourtID(courtId);

            if (availableSlotsMap.get(String.valueOf(companyId)) == null) {
                availableSlotsMap.put(String.valueOf(companyId), new ArrayList<TimeSlot>());
            }
            availableSlotsMap.get(String.valueOf(companyId)).add(ts);
        }

        return availableSlotsMap;
    }

    private int getCompanyIdFromCourtID(int courtId){

        for (Company company : ReservationDataStorage.companies){
            for(Court court : company.getCourts()){
                if(court.getCourtId() == courtId)
                    return company.getId();
            }
        }
        return -1;
    }

    private void updateSlots(){

        String url = getApplicationContext().getString(R.string.baseURL) + getApplicationContext().getString(R.string.companiesURL) + "getAll?sportId=" + ReservationDataStorage.sportId;

        final JsonArrayRequest getCourts = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("blabla", response.toString());
                //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Company>>() {}.getType();
                ReservationDataStorage.companies = gson.fromJson(response.toString(), type);
                Map<String, ArrayList<TimeSlot>> availableSlots = getAvailableSlots();

                if(!availableSlots.isEmpty()){
                    expandableListAdapter.notifyDataSetInvalidated();
                    ReservationDataStorage.availableReservations.clear();
                    for (Map.Entry<String, ArrayList<TimeSlot>> entry : availableSlots.entrySet()){
                        ReservationDataStorage.availableReservations.add(entry.getValue());
                    }
                    expandableListAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("blabla", error.toString());
            }
        });
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(getCourts);
    }
}