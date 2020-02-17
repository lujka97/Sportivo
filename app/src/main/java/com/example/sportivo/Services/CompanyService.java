package com.example.sportivo.Services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportivo.Models.Company;
import com.example.sportivo.R;
import com.example.sportivo.company_select_screen.CompanySelectAdapter;
import com.example.sportivo.company_select_screen.CompanySelectDataStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

public class CompanyService {

    public static void setCompaniesList(final Context context, final CompanySelectAdapter adapter) {
        String url = context.getString(R.string.baseURL) + context.getString(R.string.companiesURL) + "getAll?sportId=" + ReservationService.sportId;

        JsonArrayRequest getCompanies = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Company>>(){}.getType();
                CompanySelectDataStorage.companies = gson.fromJson(response.toString(), type);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(getCompanies);
    }
}
