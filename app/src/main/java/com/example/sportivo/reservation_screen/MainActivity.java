package com.example.sportivo.reservation_screen;



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
        setContentView(R.layout.rs_activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Base> tereni = new ArrayList<>();

        ArrayList<Item> sati1 = new ArrayList<>();
        sati1.add(new Item("14:00","30kn/h"));
        sati1.add(new Item("15:00","30kn/h"));
        sati1.add(new Item("16:00","30kn/h"));
        sati1.add(new Item("17:00","30kn/h"));
        sati1.add(new Item("18:00","30kn/h"));
        sati1.add(new Item("19:00","30kn/h"));
        sati1.add(new Item("20:00","30kn/h"));

        Base Teren1= new Base("Teren broj 1",sati1);
        tereni.add(Teren1);

        ArrayList<Item> sati2 = new ArrayList<>();
        sati2.add(new Item("14:00","20kn/h"));
        sati2.add(new Item("15:00","20kn/h"));
        sati2.add(new Item("16:00","20kn/h"));
        sati2.add(new Item("17:00","20kn/h"));
        sati2.add(new Item("18:00","20kn/h"));
        sati2.add(new Item("19:00","20kn/h"));
        sati2.add(new Item("20:00","20kn/h"));

        Base Teren2=new Base("Teren broj 2",sati2);
        tereni.add(Teren2);

        ItemAdapter adapter=new ItemAdapter(tereni);
        recyclerView.setAdapter(adapter);

    }
}