package com.example.sportivo.objects_screen;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.Singleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    public static ArrayList<Company> companies = new ArrayList<Company>();

    public static void fillData(final Context context, final S4_Adapter adapter) {
        /*for (int i = 0; i < cageball.length; i++) {
            Company aObject = new Company(i + 1, cageball[i]);
            listViewData.put(i, aObject);
        }*/

        String url = context.getString(R.string.baseURL) + context.getString(R.string.companiesURL) + "getAll?sportId=" + ReservationDataStorage.sportId;

        JsonArrayRequest getCompanies = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Company>>(){}.getType();
                companies = gson.fromJson(response.toString(), type);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT);
            }
        });

        Singleton.getInstance(context).addToRequestQueue(getCompanies);
    }

    private static String[] cageball = {"Športski Centar Mačak", "Cageball Winner", "SRC Kopilica", "Cageball Penal"};
}
