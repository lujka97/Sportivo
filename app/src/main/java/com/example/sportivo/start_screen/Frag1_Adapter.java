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

import com.squareup.picasso.Picasso;

public class Frag1_Adapter extends BaseAdapter {

    public static int count=0, count2=0;

        @Override
        public int getCount() {
            return Frag1_DataStorage.sports.size();
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
            if (view == null) {
                view = mInflater.inflate(R.layout.ss_frag1_listfill, viewGroup, false);
                Log.i("blabla", "view is null");
            }
            Log.i("counting", "other " + String.valueOf(++count2));

            final TextView name = (TextView) view.findViewById(R.id.name);
            ImageView imageTmb = (ImageView) view.findViewById(R.id.image_tmb);
            final Frag1_Sports sport = Frag1_DataStorage.sports.get(i);

            name.setText(sport.getName());
            //imageTmb.setImageResource(sport.getTmbImageId(myContext));
            Log.i("blabla", sport.getName() + " " + sport.getImageUrl());
            Picasso.get().load(myContext.getString(R.string.baseURL) + sport.getImageUrl()).into(imageTmb);
            /*Ion.with(imageTmb)
                    //.placeholder(R.drawable.image_1_tmb)
                    //.error(R.drawable.image_1_tmb)
                    //.animateLoad(spinAnimation)
                    //.animateIn(fadeInAnimation)
                    .load(myContext.getString(R.string.baseURL) + /*myContext.getString(R.string.imagesURL) +*/ /*sport.getImageUrl()/*sport.getName() + ".jpg"*//*)
                    .setCallback(new FutureCallback<ImageView>() {
                        @Override
                        public void onCompleted(Exception e, ImageView result) {
                            //Toast.makeText(myContext, sport.getName(), Toast.LENGTH_SHORT);
                            //Log.i("blabla", sport.getName());
                            //Log.i("blabla", result.toString());
                            Log.i("counting", "null " + String.valueOf(++count) + sport.getName() + result + e);
                            //Log.i("blabla", e.toString());
                        }
                    });*/

            return view;
        }

}
