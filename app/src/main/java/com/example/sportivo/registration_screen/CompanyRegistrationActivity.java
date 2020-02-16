package com.example.sportivo.registration_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Services.Singleton;
import com.example.sportivo.Services.UserService;
import com.example.sportivo.login_screen.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyRegistrationActivity extends AppCompatActivity {

    Button create_btn;
    TextView companyName_tv, userName_tv, password_tv, password2_tv;
    NumberPicker startsHour_np, endsHour_np;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_registration_activity);

        //back_btn = (Button) findViewById(R.id.companyRegistration_back_btn);
        companyName_tv = (TextView) findViewById(R.id.companyRegistration_name_tv);
        userName_tv = (TextView) findViewById(R.id.companyRegistration_adminName_tv);
        create_btn = (Button) findViewById(R.id.companyRegistration_createuser_btn);
        password_tv = (TextView) findViewById(R.id.companyRegistration_createpassword_tv);
        password2_tv = (TextView) findViewById(R.id.companyRegistration_confirmpass_tv);
        startsHour_np = (NumberPicker) findViewById(R.id.startsHour_numPick);
        endsHour_np = (NumberPicker) findViewById(R.id.endsHour_numPick);

        startsHour_np.setMinValue(0);
        startsHour_np.setMaxValue(23);
        endsHour_np.setMinValue(1);
        endsHour_np.setMaxValue(24);

        userName_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String url = getString(R.string.baseURL) + getString(R.string.userURL) + "checkusername?username=" + userName_tv.getText().toString();

                    StringRequest checkUsernameAvailable = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("false")){
                                        Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                                        userName_tv.setTextColor(Color.rgb(255,0,0));
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    Singleton.getInstance(getApplicationContext()).addToRequestQueue(checkUsernameAvailable);

                }
                else{
                    userName_tv.setTextColor(Color.rgb(0,0,0));
                }
            }
        });

        /*back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllRequiredFieldsFilled()){
                    String companyName = companyName_tv.getText().toString();
                    String userName = userName_tv.getText().toString();
                    String password = password2_tv.getText().toString();

                    int starts = startsHour_np.getValue();
                    int ends = endsHour_np.getValue();

                    UserService.createCompany(getApplicationContext(), companyName, userName, password, starts, ends);
                }
            }
        });
    }

    private boolean CheckAllRequiredFieldsFilled(){
        try{
            if(companyName_tv.getText().toString().equals("") || userName_tv.getText().toString().equals("") || password_tv.getText().toString().equals("")
                    || password2_tv.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Not all required fields are filled", Toast.LENGTH_LONG).show();
                return false;
            }
            else if(password2_tv.getText().toString().equals(password_tv.getText().toString()))
                return true;

            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
            return false;
        }catch(Exception ex){return false;}

    }
}
