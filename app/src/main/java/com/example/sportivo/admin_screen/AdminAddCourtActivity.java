package com.example.sportivo.admin_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportivo.R;
import com.example.sportivo.Models.Sport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminAddCourtActivity extends AppCompatActivity {
    private ArrayList<Sport> sports;
    Spinner sportsSpinner;
    Button create_btn;
    TextView courtName_tv, price_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_addcourt);

        create_btn = findViewById(R.id.admin_add_court_confirm_btn);
        courtName_tv = findViewById(R.id.admin_add_court_court_name);
        price_tv = findViewById(R.id.admin_add_court_price);
        sportsSpinner = findViewById(R.id.admin_add_court_sports_spinner);
        fillSpinner(getApplicationContext());

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckAllRequiredFieldsFilled()){
                    int position = sportsSpinner.getSelectedItemPosition();
                    String courtName = courtName_tv.getText().toString();
                    int sportId = sports.get(position).getId();
                    int price = Integer.valueOf(price_tv.getText().toString());

                    //Court court = new Court(courtName, sportId, AdminReservationsDataStorage.companyId, price);
                    try {
                        JSONObject court = new JSONObject();
                        court.put("CourtName", courtName);
                        court.put("CompanyId", AdminReservationsDataStorage.companyId);
                        court.put("SportId", sportId);
                        court.put("Price", price);

                        createCourt(court);
                    }catch (JSONException ex) { Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show(); }
                }
            }
        });
    }

    public void fillSpinner(final Context context){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.sportsURL) + "getAll";

        JsonArrayRequest getSports = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Sport>>(){}.getType();
                sports = gson.fromJson(response.toString(), type);
                ArrayList<String>sportsNames= new ArrayList<String>();
                for(Sport sport : sports){
                    sportsNames.add(sport.getName());
                }
                sportsSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.admin_add_court_spinner_item, sportsNames));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error retrieving data from server", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(getSports);
    }

    private void createCourt(final JSONObject court){

        String url = getApplicationContext().getString(R.string.baseURL) + getApplicationContext().getString(R.string.courtsURL) + "add";

        StringRequest createCourt = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //finish();
                        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                return court.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(createCourt);
    }

    private boolean CheckAllRequiredFieldsFilled(){
        try{
            if(courtName_tv.getText().toString().equals("") || price_tv.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Not all required fields are filled", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }catch(Exception ex){return false;}

    }
}
