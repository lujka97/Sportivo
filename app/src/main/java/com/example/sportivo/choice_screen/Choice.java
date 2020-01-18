package com.example.sportivo.choice_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sportivo.R;
import com.example.sportivo.objects_screen.Objects_main;

public class Choice extends AppCompatActivity {

    Button next, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cs_choice_layout);

        next = (Button) findViewById(R.id.searchBy_btn);
        back = (Button) findViewById(R.id.backChoice_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Objects_main.class);
                startActivity(intent);
            }
        });

    }
}
