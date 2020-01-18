package com.example.sportivo.reservation_screen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportivo.R;
import com.example.sportivo.ReservationDataStorage;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.util.zip.Inflater;

public class MyExpanableListViewAdapter extends BaseExpandableListAdapter {

    Context mContext;
    LayoutInflater mInflater;

    public MyExpanableListViewAdapter(Context context){
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
        return groupPosition;
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
            convertView = mInflater.inflate(R.layout.rs_baseview, parent, false);

        TextView courtName_tv = (TextView) convertView.findViewById(R.id.courtName_tv);

        try{
            courtName_tv.setText(ReservationDataStorage.courts.get(groupPosition).getCourtName());

        }catch (Error e){

        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mInflater.inflate(R.layout.rs_itemview, parent, false);

        TextView time = (TextView) convertView.findViewById(R.id.res_time_tv);
        TextView price = (TextView) convertView.findViewById(R.id.res_price_tv);
        Button make_res_btn = (Button) convertView.findViewById(R.id.res_btn);

        make_res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("blabla", groupPosition + "-" + childPosition);
                ReservationDataStorage.createReservation(mContext, groupPosition, childPosition);
            }
        });

        try{
            time.setText(ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getTimeOfDay());
            price.setText(ReservationDataStorage.availableReservations.get(groupPosition).get(childPosition).getPrice());
        }catch (Error e){
            Log.e("blabla", e.toString());
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
