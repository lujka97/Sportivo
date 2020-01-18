package com.example.sportivo.start_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sportivo.R;

public class Frag2 extends Fragment {

    private View root;
    ListView my_reservations_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ss_frag2_layout,container,false);

        my_reservations_list = (ListView) root.findViewById(R.id.my_reservations_list_view);

        Frag2_Adapter reservationsAdapter = new Frag2_Adapter(root.getContext());
        Frag2_DataStorage.fillData(root.getContext(), reservationsAdapter);
        my_reservations_list.setAdapter(reservationsAdapter);

        return root;
    }
}
