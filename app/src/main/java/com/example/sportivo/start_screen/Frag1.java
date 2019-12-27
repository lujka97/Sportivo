package com.example.sportivo.start_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.sportivo.R;

public class Frag1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ss_frag1_layout, container, false);
        ListView myListview=(ListView)root.findViewById(R.id.tab1_listview);
        Frag1_DataStorage.fillData();
        myListview.setAdapter((ListAdapter) new Frag1_Adapter(root.getContext()));
        return root;
    }
}
