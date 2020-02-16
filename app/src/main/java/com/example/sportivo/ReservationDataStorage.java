package com.example.sportivo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.admin_screen.AdminMainActivity;
import com.example.sportivo.objects_screen.Company;
import com.example.sportivo.reservation_screen.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ReservationDataStorage {

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

    public static int getYear(){ return ReservationDataStorage.year; }
    public static int getMonth() { return ReservationDataStorage.month;}
    public static int getDay() { return ReservationDataStorage.day; }
    public static int getHour() { return ReservationDataStorage.hour; }
    public static int getMinute() { return ReservationDataStorage.minute; }
    public static int getSecond() { return ReservationDataStorage.second; }
    public static int getLength() { return ReservationDataStorage.length; }

    public static String getDate(){
        return ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setDate(Context context){
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        ReservationDataStorage.year = cal.get(Calendar.YEAR);
        ReservationDataStorage.month = cal.get(Calendar.MONTH) + 1;
        ReservationDataStorage.day = cal.get(Calendar.DAY_OF_MONTH);

        ReservationDataStorage.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setDate(Context context, int year, int month, int day){

        ReservationDataStorage.year = year;
        ReservationDataStorage.month = month + 1;
        ReservationDataStorage.day = day;

        Log.i("blabla", String.valueOf(ReservationDataStorage.month));

        ReservationDataStorage.url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;
    }

    public static void setTime(Context context, int hour, int minute, int second){
        ReservationDataStorage.hour = hour;
        ReservationDataStorage.minute = minute;
        ReservationDataStorage.second = second;

        ReservationDataStorage.urlResByHour = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "getOnDateTime?date="
                + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day + "T"
                + String.format("%02d", ReservationDataStorage.hour) + ":" + String.format("%02d", ReservationDataStorage.minute) + ":" + String.format("%02d", ReservationDataStorage.second);
    }

    public static void createReservation(final Context context, final int groupPosition, final int childPosition){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "create?start="
                + ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getStartTimeOfDayWithMinutes()
                + "&end=" + ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getEndTimeOfDayWithMinutes();

        StringRequest createReservation = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reservation successfully created", Toast.LENGTH_LONG).show();
                    Intent intent;
                    if(ReservationDataStorage.isAdmin){
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
                    reservation.put("StartTime", ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getStartTimeOfDayWithMinutes());
                    reservation.put("EndTime", ReservationDataStorage.getDate() + "T" + ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getEndTimeOfDayWithMinutes());
                    reservation.put("CourtId", ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getCourtId());
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
}
