package com.example.sportivo.start_screen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.choice_screen.Choice;

public class Frag1 extends Fragment {

    private View root;
    ListView sportsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ss_frag1_layout, container, false);
        sportsList=(ListView)root.findViewById(R.id.tab1_listview);

        Frag1_Adapter sportsAdapter = new Frag1_Adapter(root.getContext());
        Frag1_DataStorage.fillData(root.getContext(), sportsAdapter);
        sportsList.setAdapter(sportsAdapter);

        sportsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReservationDataStorage.sportId = Frag1_DataStorage.sports.get(position).getId();
                Intent intent = new Intent(root.getContext(), Choice.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
