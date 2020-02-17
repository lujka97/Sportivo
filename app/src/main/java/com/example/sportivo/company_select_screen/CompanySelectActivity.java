package com.example.sportivo.company_select_screen;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sportivo.R;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.reservation_screen.ReservationActivity;

public class CompanySelectActivity extends AppCompatActivity {

    ListView companiesListView;
    CompanySelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_selection_activity);
        companiesListView =(ListView) findViewById(R.id.companies_list);
        adapter = new CompanySelectAdapter(getApplicationContext());
        companiesListView.setAdapter(adapter);
        CompanySelectDataStorage.fillData(getApplicationContext(), adapter);

        companiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("blabla", "item clicked");
                ReservationService.selectedCompany = CompanySelectDataStorage.companies.get(position);
                ReservationService.companyId = ReservationService.selectedCompany.getCompanyId();
                Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                startActivity(intent);
            }
        });
    }
}
