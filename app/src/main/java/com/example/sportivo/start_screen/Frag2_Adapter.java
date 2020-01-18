package com.example.sportivo.start_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;

import com.example.sportivo.Reservation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class Frag2_Adapter extends BaseAdapter {

    @Override
    public int getCount() {
        return Frag2_DataStorage.reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return Frag2_DataStorage.reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Context myContext;
    private LayoutInflater mInflater;

    public Frag2_Adapter(Context context) {
        myContext = context;
        mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.ss_frag2_listfill, viewGroup, false);
        }

        final TextView companyName = (TextView) view.findViewById(R.id.reservationCompanyName);
        final TextView time = (TextView) view.findViewById(R.id.reservationDateTime);
        final Reservation reservation = Frag2_DataStorage.reservations.get(i);

        time.setText(SimpleDateFormat.getDateTimeInstance().format(reservation.getStartTime()));
        companyName.setText(reservation.getCourt().getCompany().getName());

        return view;
    }

}
