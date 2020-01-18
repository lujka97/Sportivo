package com.example.sportivo.objects_screen;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.reservation_screen.S5_Main;

public class Objects_main extends AppCompatActivity {

    ListView companiesListView;
    S4_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.os_activity_objects);
        companiesListView =(ListView) findViewById(R.id.companies_list);
        adapter = new S4_Adapter(getApplicationContext());
        companiesListView.setAdapter(adapter);
        DataStorage.fillData(getApplicationContext(), adapter);

        companiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("blabla", "item clicked");
                ReservationDataStorage.companyId = DataStorage.companies.get(position).getId();
                Intent intent = new Intent(getApplicationContext(), S5_Main.class);
                startActivity(intent);
            }
        });
    }
}
