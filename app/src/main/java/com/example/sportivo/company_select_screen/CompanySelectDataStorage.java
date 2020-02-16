package com.example.sportivo.company_select_screen;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.Models.Company;
import com.example.sportivo.R;
import com.example.sportivo.Services.CompanyService;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.Services.Singleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CompanySelectDataStorage {
    public static ArrayList<Company> companies = new ArrayList<Company>();

    public static void fillData(final Context context, final CompanySelectAdapter adapter) {

        CompanyService.setCompaniesList(context, adapter);
    }
}
