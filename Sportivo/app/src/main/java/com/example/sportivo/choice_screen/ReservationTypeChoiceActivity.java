package com.example.sportivo.choice_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sportivo.R;
import com.example.sportivo.company_select_screen.CompanySelectActivity;
import com.example.sportivo.reservationbyhour_screen.ReservationByHourActivity;

public class ReservationTypeChoiceActivity extends AppCompatActivity {

    Button next, back;
    RadioButton searchByTime, searchByPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_type_choice_activity);

        next = (Button) findViewById(R.id.searchBy_btn);
        back = (Button) findViewById(R.id.backChoice_btn);
        searchByPlace = (RadioButton) findViewById(R.id.place_rb);
        searchByTime = (RadioButton) findViewById(R.id.time_rb);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(searchByPlace.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), CompanySelectActivity.class);
                    startActivity(intent);
                }else if(searchByTime.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), ReservationByHourActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Neither option is selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
