package com.example.sportivo.time_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;

import java.util.Calendar;
import java.util.Date;

public class TimeSelect extends AppCompatActivity {

    Button confirmTime;
    NumberPicker numpick;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ts_choose);

        confirmTime = (Button) findViewById(R.id.confirmTime_btn);
        numpick = (NumberPicker) findViewById(R.id.numpick);
        test = (TextView) findViewById(R.id.test);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if(cal.get(Calendar.YEAR) == ReservationDataStorage.getYear() &&
                cal.get(Calendar.MONTH) == (ReservationDataStorage.getMonth()-1) &&
                cal.get(Calendar.DAY_OF_MONTH) == ReservationDataStorage.getDay()){
            numpick.setMinValue(cal.get(Calendar.HOUR_OF_DAY)+1);
        }else{
            numpick.setMinValue(0);
        }

        numpick.setMaxValue(23);

        ReservationDataStorage.setTime(getApplicationContext(),numpick.getValue() , 0 ,0);

        confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        numpick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                test.setText("Ovo je ura "+newVal);
                ReservationDataStorage.setTime(getApplicationContext(), newVal, ReservationDataStorage.getMinute(), ReservationDataStorage.getSecond());
            }
        });

    }

}
