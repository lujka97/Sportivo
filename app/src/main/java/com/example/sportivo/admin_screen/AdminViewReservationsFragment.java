package com.example.sportivo.admin_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sportivo.R;
import com.example.sportivo.time_screen.DateSelectActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminViewReservationsFragment extends Fragment {
    private View root;
    FloatingActionButton createCourt_btn;
    TextView date_tv;
    AdminReservationsAdapter reservationsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.admin_reservations_fragment, container, false);
        ListView reservationsList = (ListView) root.findViewById(R.id.admin_reservations_list_view);
        createCourt_btn = (FloatingActionButton) root.findViewById(R.id.admin_add_court_fab);
        date_tv = (TextView) root.findViewById(R.id.admin_reservations_date);

        AdminReservationsDataStorage.date = new Date();

        reservationsAdapter = new AdminReservationsAdapter(root.getContext());
        AdminReservationsDataStorage.fillData(root.getContext(), reservationsAdapter);
        reservationsList.setAdapter(reservationsAdapter);

        createCourt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), AdminAddCourtActivity.class);
                startActivity(intent);
            }
        });

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), DateSelectActivity.class);
                intent.putExtra("admin", 1);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Calendar cal = Calendar.getInstance();
        cal.setTime(AdminReservationsDataStorage.date);

        date_tv.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()));

        AdminReservationsDataStorage.fillData(root.getContext(), reservationsAdapter);

    }
}
