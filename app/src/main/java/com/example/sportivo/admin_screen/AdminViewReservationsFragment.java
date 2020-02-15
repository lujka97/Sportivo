package com.example.sportivo.admin_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sportivo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminViewReservationsFragment extends Fragment {
    private View root;
    FloatingActionButton createCourt_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.admin_layout, container, false);
        ListView reservationsList = (ListView) root.findViewById(R.id.admin_reservations_list_view);
        createCourt_btn = (FloatingActionButton) root.findViewById(R.id.admin_add_court_fab);

        AdminReservationsAdapter reservationsAdapter = new AdminReservationsAdapter(root.getContext());
        AdminReservationsDataStorage.fillData(root.getContext(), reservationsAdapter);
        reservationsList.setAdapter(reservationsAdapter);

        createCourt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), AdminAddCourt.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
