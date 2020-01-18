package com.example.sportivo.start_screen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Singleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Frag1_DataStorage {

        public static ArrayList<Frag1_Sports> sports = new ArrayList<Frag1_Sports>();

        public static void fillData(final Context context) {

            String url = context.getString(R.string.baseURL) + context.getString(R.string.sportsURL) + "getAll";

            JsonArrayRequest getSports = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Frag1_Sports>>(){}.getType();
                    sports = gson.fromJson(response.toString(), type);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT);
                }
            });

            Singleton.getInstance(context).addToRequestQueue(getSports);

        }

        private static String[] names = {"Cageball", "Tenis", "Futsal", "Stolni tenis", "Biljar", "Kuglanje"};
    }

