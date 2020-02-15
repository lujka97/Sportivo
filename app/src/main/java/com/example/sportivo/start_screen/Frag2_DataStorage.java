package com.example.sportivo.start_screen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.Singleton;
import com.example.sportivo.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frag2_DataStorage {

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public static void fillData(final Context context) {

        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "getMy";

        Log.i("blabla", url);

        JsonArrayRequest getReservations = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("blabla", response.toString());
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Type type = new TypeToken<List<Reservation>>(){}.getType();
                reservations = gson.fromJson(response.toString(), type);

                Frag2.reservationsAdapter.notifyDataSetChanged();
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

