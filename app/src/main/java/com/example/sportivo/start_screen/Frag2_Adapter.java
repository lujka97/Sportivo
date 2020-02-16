package com.example.sportivo.start_screen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.CalendarService;
import com.example.sportivo.R;

import com.example.sportivo.Reservation;
import com.example.sportivo.ReservationDataStorage;
import com.example.sportivo.Singleton;
import com.example.sportivo.TokenManager;
import com.example.sportivo.admin_screen.AdminMainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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

        final Button cancel = (Button) view.findViewById(R.id.cancel_reservation);

        final TextView companyName = (TextView) view.findViewById(R.id.reservationCompanyName);
        final TextView time = (TextView) view.findViewById(R.id.reservationDateTime);
        final Reservation reservation = Frag2_DataStorage.reservations.get(i);

        time.setText(SimpleDateFormat.getDateTimeInstance().format(reservation.getStartTime()));
        companyName.setText(reservation.getCourt().getCompany().getName());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReservation(myContext, reservation.getReservationId());

            }
        });

        return view;
    }

    private void deleteReservation(final Context context, final int reservationId){

        String url = context.getString(R.string.baseURL) + context.getString(R.string.reservationsURL) + "delete?reservationId=" + reservationId;

        StringRequest deleteReservation = new StringRequest(StringRequest.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reservation deleted", Toast.LENGTH_SHORT).show();

                    Reservation reservation = Frag2_DataStorage.getReservationById(reservationId);
                    CalendarService.removeEvent(context, reservation);
                    Frag2_DataStorage.fillData(context);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("blabla", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  authParams = new HashMap<>();

                authParams.put("Authorization", "Bearer " + TokenManager.getToken());
                return authParams;
            }

        };

        Singleton.getInstance(context).addToRequestQueue(deleteReservation);
    }

}
