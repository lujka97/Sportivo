package com.example.sportivo.time_screen;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sportivo.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class Time extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ts_time);

        Date today = new Date();
        Calendar next_month = Calendar.getInstance();
        next_month.add(Calendar.MONTH, 1);
        CalendarPickerView datepicker = findViewById(R.id.calendar);
        datepicker.init(today, next_month.getTime()).withSelectedDate(today);

        datepicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {


                }



            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

}
