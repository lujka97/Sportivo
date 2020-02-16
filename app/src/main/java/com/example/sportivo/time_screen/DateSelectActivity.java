package com.example.sportivo.time_screen;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.sportivo.R;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.admin_screen.AdminReservationsDataStorage;

import java.util.Calendar;

public class DateSelectActivity extends AppCompatActivity {

    //CalendarPickerView datePicker;
    DatePicker datePicker;
    Button confimDate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_select_activity);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        confimDate_btn = (Button) findViewById(R.id.confirmDate_btn);

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        confimDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().getIntExtra("admin", 0) == 1){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    AdminReservationsDataStorage.date.setTime(calendar.getTimeInMillis());

                }else {
                    ReservationService.setDate(getApplicationContext(), datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                    if (getIntent().getIntExtra("time", 0) == 1) {
                        Intent intent = new Intent(getApplicationContext(), TimeSelectActivity.class);
                        startActivity(intent);
                    }
                }

                finish();
            }
        });
    }

}
