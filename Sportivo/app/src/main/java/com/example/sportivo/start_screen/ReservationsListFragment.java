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

public class ReservationsListFragment extends Fragment {

    private View root;
    ListView my_reservations_list;
    ReservationsListFragmentAdapter reservationsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.reservations_list_fragment,container,false);

        my_reservations_list = (ListView) root.findViewById(R.id.my_reservations_list_view);

        reservationsAdapter = new ReservationsListFragmentAdapter(root.getContext(), my_reservations_list);
        ReservationsListFragmentDataStorage.fillData(root.getContext(), reservationsAdapter);
        my_reservations_list.setAdapter(reservationsAdapter);

        return root;
    }

}
