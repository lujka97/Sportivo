package com.example.sportivo.company_select_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sportivo.Models.Company;
import com.example.sportivo.R;


public class CompanySelectAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return CompanySelectDataStorage.companies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private Context myContext;
    private LayoutInflater mInflater;

    public CompanySelectAdapter(Context context) {
        myContext = context;
        mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = mInflater.inflate(R.layout.company_selection_list_item, viewGroup, false);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final Company company = CompanySelectDataStorage.companies.get(i);
        name.setText(company.getName());

        return view;
    }
}
