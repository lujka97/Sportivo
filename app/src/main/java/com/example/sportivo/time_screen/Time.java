package com.example.sportivo.time_screen;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.Singleton;
import com.example.sportivo.objects_screen.Objects;
import com.example.sportivo.reservation_screen.S5_Main;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Time extends AppCompatActivity {

    //CalendarPickerView datePicker;
    DatePicker datePicker;
    Button confimDate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ts_time);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        confimDate_btn = (Button) findViewById(R.id.confirmDate_btn);

        confimDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationDataStorage.year = datePicker.getYear();
                ReservationDataStorage.month = datePicker.getMonth() + 1;
                ReservationDataStorage.day = datePicker.getDayOfMonth();

                final String url = getApplicationContext().getString(R.string.baseURL) + getApplicationContext().getString(R.string.reservationsURL) + "get?companyId=" +
                        ReservationDataStorage.companyId + "&date=" + ReservationDataStorage.year + "-" + ReservationDataStorage.month + "-" + ReservationDataStorage.day;

                ReservationDataStorage.url = url;

                finish();
            }
        });


        /*Date today = new Date();
        Calendar next_month = Calendar.getInstance();
        next_month.add(Calendar.MONTH, 5);
        datePicker = findViewById(R.id.calendar);
        datePicker.init(today, next_month.getTime()).withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {


                }



            @Override
            public void onDateUnselected(Date date) {

            }
        });*/
    }

}
