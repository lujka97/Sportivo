package com.example.sportivo.time_screen;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.reservation_screen.MainActivity;

public class DateSelect extends AppCompatActivity {

    //CalendarPickerView datePicker;
    DatePicker datePicker;
    Button confimDate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ts_time);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        confimDate_btn = (Button) findViewById(R.id.confirmDate_btn);

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        confimDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReservationDataStorage.setDate(getApplicationContext(), datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                if (getIntent().getIntExtra("time", 0) == 1){
                    Intent intent = new Intent(getApplicationContext(), TimeSelect.class);
                    startActivity(intent);
                }

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
                startActivity(new Intent(DateSelect.this,PopUp.class));

                }



            @Override
            public void onDateUnselected(Date date) {

            }
        });*/
    }

}
