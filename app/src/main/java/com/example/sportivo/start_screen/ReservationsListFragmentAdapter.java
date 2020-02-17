package com.example.sportivo.start_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sportivo.R;

import com.example.sportivo.Models.Reservation;
import com.example.sportivo.Services.ReservationService;

import java.text.SimpleDateFormat;

public class ReservationsListFragmentAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return ReservationsListFragmentDataStorage.reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return ReservationsListFragmentDataStorage.reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Context myContext;
    private LayoutInflater mInflater;
    private ListView mListView;

    public ReservationsListFragmentAdapter(Context context, ListView listView) {
        myContext = context;
        mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = listView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.reservations_list_fragment_item, viewGroup, false);
        }

        final Button cancel = (Button) view.findViewById(R.id.cancel_reservation);

        final TextView companyName = (TextView) view.findViewById(R.id.reservationCompanyName);
        final TextView time = (TextView) view.findViewById(R.id.reservationDateTime);
        final Reservation reservation = ReservationsListFragmentDataStorage.reservations.get(i);

        time.setText(SimpleDateFormat.getDateTimeInstance().format(reservation.getStartTime()));
        companyName.setText(reservation.getCourt().getCompany().getName());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationService.deleteReservation(myContext, reservation, (ReservationsListFragmentAdapter) mListView.getAdapter());
            }
        });

        return view;
    }
}
