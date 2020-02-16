package com.example.sportivo.start_screen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.sportivo.R;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.admin_screen.AdminReservationsDataStorage;
import com.example.sportivo.choice_screen.ReservationTypeChoiceActivity;
import com.example.sportivo.reservation_screen.ReservationActivity;

public class SportsListFragment extends Fragment {

    private View root;
    ListView sportsList;
    SportsListFragmentAdapter sportsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.sports_list_fragment, container, false);
        sportsList=(ListView)root.findViewById(R.id.tab1_listview);

        sportsAdapter = new SportsListFragmentAdapter(root.getContext());
        SportsListFragmentDataStorage.fillData(root.getContext(), sportsAdapter, ReservationService.isAdmin);
        sportsList.setAdapter(sportsAdapter);

        sportsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReservationService.sportId = SportsListFragmentDataStorage.sports.get(position).getId();

                if(ReservationService.isAdmin){
                    ReservationService.companyId = AdminReservationsDataStorage.companyId;
                    Intent intent = new Intent(root.getContext(), ReservationActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(root.getContext(), ReservationTypeChoiceActivity.class);
                    startActivity(intent);
                }
            }
        });
        return root;
    }
}
