package com.example.sportivo.reservationbyhour_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportivo.Court;
import com.example.sportivo.R;
import com.example.sportivo.Reservation;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.objects_screen.Company;

public class MyExpandableListViewAdapterResByHour extends BaseExpandableListAdapter {

    Context mContext;
    LayoutInflater mInflater;

    public MyExpandableListViewAdapterResByHour(Context context){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getGroupCount() {
        return ReservationDataStorage.availableReservations.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ReservationDataStorage.availableReservations.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ReservationDataStorage.availableReservations.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.rsh_baseview, parent, false);

        TextView companyName_tv = (TextView) convertView.findViewById(R.id.companyName_tv);

        try{
            companyName_tv.setText(ReservationDataStorage.companies.get(groupPosition).getName());

        }catch (Error e){

        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.rsh_itemview, parent, false);

        TextView courtName = (TextView) convertView.findViewById(R.id.courtNameResByH_tv);
        TextView price = (TextView) convertView.findViewById(R.id.resByH_price_tv);
        Button make_res_btn = (Button) convertView.findViewById(R.id.resByH_btn);

        make_res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("blabla", groupPosition + "-" + childPosition);
                ReservationDataStorage.createReservation(mContext, groupPosition, childPosition);
            }
        });

        int courtId = ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getCourtId();
        Court courtSelected = new Court();
        for (Company company : ReservationDataStorage.companies){
            for (Court court : company.getCourts()){
                if(court.getCourtId() == courtId){
                    courtSelected = court;
                    break;
                }
            }
        }

        try{
            courtName.setText(courtSelected.getCourtName());
            price.setText(ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getPrice());
        }catch (Error e){

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
