package com.example.sportivo.admin_screen;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.R;
import com.example.sportivo.Models.Reservation;
import com.example.sportivo.Services.Singleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminReservationsDataStorage {

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    public static int companyId=-1;
    public static Date date;

    public static void fillData(final Context context, final AdminReservationsAdapter adapter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "get?companyId=" +
                companyId + "&date=" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1)  + "-" + cal.get(Calendar.DAY_OF_MONTH);

        JsonArrayRequest getSports = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("blabla", response.toString());
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type type = new TypeToken<List<Reservation>>(){}.getType();
                reservations = gson.fromJson(response.toString(), type);

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(getSports);
    }
}
