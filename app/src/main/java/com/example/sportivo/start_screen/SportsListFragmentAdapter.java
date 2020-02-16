package com.example.sportivo.start_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportivo.R;

import com.example.sportivo.Models.Sport;
import com.squareup.picasso.Picasso;

public class SportsListFragmentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return SportsListFragmentDataStorage.sports.size();
        }

        @Override
        public Object getItem(int position) {
            return SportsListFragmentDataStorage.sports.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private Context myContext;
        private LayoutInflater mInflater;

        public SportsListFragmentAdapter(Context context) {
            myContext = context;
            mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mInflater.inflate(R.layout.sports_list_fragment_item, viewGroup, false);
            }

            final TextView name = (TextView) view.findViewById(R.id.name);
            ImageView imageTmb = (ImageView) view.findViewById(R.id.image_tmb);
            final Sport sport = SportsListFragmentDataStorage.sports.get(i);

            name.setText(sport.getName());
            Picasso.get().load(myContext.getString(R.string.baseURL) + sport.getImageUrl()).into(imageTmb);

            return view;
        }

}
