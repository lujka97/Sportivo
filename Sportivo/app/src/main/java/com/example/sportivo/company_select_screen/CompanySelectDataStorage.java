package com.example.sportivo.company_select_screen;

import android.content.Context;

import com.example.sportivo.Models.Company;
import com.example.sportivo.Services.CompanyService;

import java.util.ArrayList;

public class CompanySelectDataStorage {
    public static ArrayList<Company> companies = new ArrayList<Company>();

    public static void fillData(final Context context, final CompanySelectAdapter adapter) {

        CompanyService.setCompaniesList(context, adapter);
    }
}
