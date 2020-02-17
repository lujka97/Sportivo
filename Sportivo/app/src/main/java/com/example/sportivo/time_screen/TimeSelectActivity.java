package com.example.sportivo.time_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.sportivo.Models.Reservation;
import com.example.sportivo.R;
import com.example.sportivo.Services.ReservationService;

import java.util.Calendar;
import java.util.Date;

public class TimeSelectActivity extends AppCompatActivity {

    Button confirmTime;
    NumberPicker numpick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_select_activity);

        confirmTime = (Button) findViewById(R.id.confirmTime_btn);
        numpick = (NumberPicker) findViewById(R.id.numpick);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if(cal.get(Calendar.YEAR) == ReservationService.getYear() &&
                cal.get(Calendar.MONTH) == (ReservationService.getMonth()-1) &&
                cal.get(Calendar.DAY_OF_MONTH) == ReservationService.getDay()){
            numpick.setMinValue(cal.get(Calendar.HOUR_OF_DAY)+1);
        }else{
            numpick.setMinValue(0);
        }

        numpick.setMaxValue(23);

        ReservationService.setTime(getApplicationContext(),numpick.getValue() , 0 ,0);

        confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationService.setTime(getApplicationContext(), numpick.getValue(), ReservationService.getMinute(), ReservationService.getSecond());
                finish();
            }
        });
    }

}
