package com.example.sportivo.reservationbyhour_screen;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rsh_activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Base> objekti = new ArrayList<>();

        ArrayList<Item> tereni1 = new ArrayList<>();
        tereni1.add(new Item("Teren broj 1","30kn/h"));
        tereni1.add(new Item("Teren broj 2","30kn/h"));

        Base Objekt1= new Base("Objekt 1",tereni1);
        objekti.add(Objekt1);

        ArrayList<Item> tereni2 = new ArrayList<>();
        tereni2.add(new Item("14:00","20kn/h"));
        tereni2.add(new Item("15:00","20kn/h"));
        tereni2.add(new Item("16:00","20kn/h"));

        Base Objekt2=new Base("Objekt 2",tereni2);
        objekti.add(Objekt2);

        ItemAdapter adapter=new ItemAdapter(objekti);
        recyclerView.setAdapter(adapter);

    }
}