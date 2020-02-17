package com.example.sportivo.Services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportivo.Models.Sport;
import com.example.sportivo.R;
import com.example.sportivo.start_screen.SportsListFragmentAdapter;
import com.example.sportivo.start_screen.SportsListFragmentDataStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

public class SportService {

    public static void setSports(final Context context, final SportsListFragmentAdapter adapter){

        String url = context.getString(R.string.baseURL) + context.getString(R.string.sportsURL) + "getAll";

        JsonArrayRequest getSports = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("blabla", response.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<Sport>>(){}.getType();
                SportsListFragmentDataStorage.sports = gson.fromJson(response.toString(), type);

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(getSports);
    }

    public static void setSportsForCompany(final Context context, final SportsListFragmentAdapter adapter, int companyId){

        String url = context.getString(R.string.baseURL) + context.getString(R.string.sportsURL) + "getAllForCompany?companyId=" + companyId;

        JsonArrayRequest getSports = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("blabla", response.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<Sport>>(){}.getType();
                SportsListFragmentDataStorage.sports = gson.fromJson(response.toString(), type);

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(getSports);
    }
}
