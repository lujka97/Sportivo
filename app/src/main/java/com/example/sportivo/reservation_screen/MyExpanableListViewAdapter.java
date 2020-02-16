package com.example.sportivo.reservation_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportivo.R;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.Models.TimeSlot;

public class MyExpanableListViewAdapter extends BaseExpandableListAdapter {

    Context mContext;
    LayoutInflater mInflater;

    public MyExpanableListViewAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getGroupCount() {
        return ReservationService.availableReservations.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ReservationService.availableReservations.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ReservationService.availableReservations.get(groupPosition).get(childPosition);
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
            convertView = mInflater.inflate(R.layout.reservation_list_group_item, parent, false);

        TextView courtName_tv = (TextView) convertView.findViewById(R.id.courtName_tv);

        try{
            courtName_tv.setText(ReservationService.courts.get(groupPosition).getCourtName());

        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mInflater.inflate(R.layout.reservation_list_child_item, parent, false);

        TextView time = (TextView) convertView.findViewById(R.id.res_time_tv);
        TextView price = (TextView) convertView.findViewById(R.id.res_price_tv);
        Button make_res_btn = (Button) convertView.findViewById(R.id.res_btn);

        make_res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("blabla", groupPosition + "-" + childPosition);
                TimeSlot time = ReservationService.availableReservations.get(groupPosition).get(childPosition);
                ReservationService.setTime(mContext, time.getStartsHour(), time.getStartsMinutes(), 0);
                ReservationService.createReservation(mContext, groupPosition, childPosition);
            }
        });

        try{
            time.setText(ReservationService.availableReservations.get(groupPosition).get(childPosition).getTimeOfDay());
            price.setText(ReservationService.availableReservations.get(groupPosition).get(childPosition).getPriceString());
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
