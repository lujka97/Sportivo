package com.example.sportivo.Services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.Models.Company;
import com.example.sportivo.Models.Court;
import com.example.sportivo.Models.Reservation;
import com.example.sportivo.Models.TimeSlot;
import com.example.sportivo.R;
import com.example.sportivo.admin_screen.AdminMainActivity;
import com.example.sportivo.start_screen.ReservationsListFragment;
import com.example.sportivo.start_screen.ReservationsListFragmentAdapter;
import com.example.sportivo.start_screen.ReservationsListFragmentDataStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReservationService {

    public static int sportId;
    public static int companyId;
    private static int year, month, day;
    private static int hour, minute, second;
    private static int length = 1;
    public static String date;
    public static boolean isAdmin;

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    public static ArrayList<Court> courts = new ArrayList<Court>();
    public static List<List<TimeSlot>> availableReservations = new ArrayList<List<TimeSlot>>();
    public static String url="";
    public static String urlResByHour="";

    public static ArrayList<Company> companies = new ArrayList<Company>();
    public static Company selectedCompany;

    public static int getYear(){ return ReservationService.year; }
    public static int getMonth() { return ReservationService.month;}
    public static int getDay() { return ReservationService.day; }
    public static int getHour() { return ReservationService.hour; }
    public static int getMinute() { return ReservationService.minute; }
    public static int getSecond() { return ReservationService.second; }
    public static int getLength() { return ReservationService.length; }

    public static String getDate(){
        return ReservationService.year + "-" + ReservationService.month + "-" + ReservationService.day;
    }
    public static String getEndDate(TimeSlot slot){
        if (slot.getEndsHour() < slot.getStartsHour()){
            return ReservationService.year + "-" + ReservationService.month + "-" + (ReservationService.day + 1);
        }

        return ReservationService.year + "-" + ReservationService.month + "-" + ReservationService.day;
    }

    public static void setDate(Context context){
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        ReservationService.year = cal.get(Calendar.YEAR);
        ReservationService.month = cal.get(Calendar.MONTH) + 1;
        ReservationService.day = cal.get(Calendar.DAY_OF_MONTH);

        ReservationService.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationService.companyId + "&date=" + ReservationService.year + "-" + ReservationService.month + "-" + ReservationService.day;
    }

    public static void setDate(Context context, int year, int month, int day){

        ReservationService.year = year;
        ReservationService.month = month + 1;
        ReservationService.day = day;

        Log.i("blabla", String.valueOf(ReservationService.month));

        ReservationService.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationService.companyId + "&date=" + ReservationService.year + "-" + ReservationService.month + "-" + ReservationService.day;
    }

    public static void setTime(Context context, int hour, int minute, int second){
        ReservationService.hour = hour;
        ReservationService.minute = minute;
        ReservationService.second = second;

        ReservationService.urlResByHour = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "getOnDateTime?date="
                + ReservationService.year + "-" + ReservationService.month + "-" + ReservationService.day + "T"
                + String.format(Locale.GERMAN, "%02d", ReservationService.hour) + ":" + String.format(Locale.GERMAN, "%02d", ReservationService.minute) + ":" + String.format(Locale.GERMAN, "%02d", ReservationService.second);
    }

    public static void createReservation(final Context context, final int groupPosition, final int childPosition){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "create?start="
                + ReservationService.getDate() + "T" + ReservationService.availableReservations.get(groupPosition).get(childPosition).getStartTimeOfDayWithMinutes()
                + "&end=" + ReservationService.getEndDate(ReservationService.availableReservations.get(groupPosition).get(childPosition))
                + "T" + ReservationService.availableReservations.get(groupPosition).get(childPosition).getEndTimeOfDayWithMinutes();

        StringRequest createReservation = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reservation successfully created", Toast.LENGTH_LONG).show();
                    Intent intent;
                    if(ReservationService.isAdmin){
                        intent = new Intent(context, AdminMainActivity.class);
                    }else {
                        CalendarService.addEventToCalendar(context);
                        intent = new Intent(context, com.example.sportivo.start_screen.MainActivity.class);
                    }

                    context.startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("blabla", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  authParams = new HashMap<>();

                authParams.put("Authorization", "Bearer " + TokenManager.getToken());
                return authParams;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject reservation = new JSONObject();
                try{
                    reservation.put("CourtId", ReservationService.availableReservations.get(groupPosition).get(childPosition).getCourtId());
                } catch(JSONException e){ e.printStackTrace(); }

                return reservation.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Singleton.getInstance(context).addToRequestQueue(createReservation);
    }

    public static void deleteReservation(final Context context, final Reservation reservation, final ReservationsListFragmentAdapter adapter){

        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "delete?reservationId=" + reservation.getReservationId();

        StringRequest deleteReservation = new StringRequest(StringRequest.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reservation deleted", Toast.LENGTH_SHORT).show();

                    CalendarService.removeEvent(context, reservation);
                    //setReservations(context, adapter);
                    ReservationsListFragmentDataStorage.removeReservation(reservation.getReservationId());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("blabla", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  authParams = new HashMap<>();

                authParams.put("Authorization", "Bearer " + TokenManager.getToken());
                return authParams;
            }

        };

        Singleton.getInstance(context).addToRequestQueue(deleteReservation);
    }

    public static void setReservations(final Context context, final ReservationsListFragmentAdapter adapter){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "getMy";

        JsonArrayRequest getReservations = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type type = new TypeToken<List<Reservation>>(){}.getType();
                ReservationsListFragmentDataStorage.reservations = gson.fromJson(response.toString(), type);

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  authParams = new HashMap<>();

                authParams.put("Authorization", "Bearer " + TokenManager.getToken());
                return authParams;
            }
        };

        Singleton.getInstance(context).addToRequestQueue(getReservations);
    }
}
