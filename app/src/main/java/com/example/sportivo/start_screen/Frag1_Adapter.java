package com.example.sportivo.start_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportivo.R;

public class Frag1_Adapter extends BaseAdapter {


        @Override
        public int getCount() {
            return Frag1_DataStorage.listViewData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        private Context myContext;
        private LayoutInflater mInflater;

        public Frag1_Adapter(Context context) {
            myContext = context;
            mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = mInflater.inflate(R.layout.ss_frag1_listfill, viewGroup, false);
            final TextView name = (TextView) view.findViewById(R.id.name);
            final ImageView imageTmb = (ImageView) view.findViewById(R.id.image_tmb);
            final Frag1_Sports sport = Frag1_DataStorage.listViewData.get(i);
            name.setText(sport.getName());
            imageTmb.setImageResource(sport.getTmbImageId(myContext));
            return view;
        }

}
