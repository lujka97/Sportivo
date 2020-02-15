package com.example.sportivo.admin_screen;

import android.content.Context;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.objects_screen.Company;
import com.example.sportivo.objects_screen.DataStorage;

public class AdminReservationsAdapter extends BaseAdapter {

    private Context myContext;
    private LayoutInflater mInflater;

    public AdminReservationsAdapter(Context context){
        myContext = context;
        mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return AdminReservationsDataStorage.reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return AdminReservationsDataStorage.reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.admin_reservation_item_view, parent, false);

        final TextView name = (TextView) convertView.findViewById(R.id.admin_reservations_view_court_name);
        final TextView time = (TextView) convertView.findViewById(R.id.admin_reservation_view_DateTime);

        final Reservation reservation = AdminReservationsDataStorage.reservations.get(position);
        name.setText(reservation.getCourt().getCourtName());
        time.setText(reservation.getStartTime().toString());

        return convertView;
    }
}
