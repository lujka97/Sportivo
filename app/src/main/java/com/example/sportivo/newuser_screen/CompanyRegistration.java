package com.example.sportivo.newuser_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Singleton;
import com.example.sportivo.login_screen.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CompanyRegistration extends AppCompatActivity {

    Button back_btn, create_btn;
    TextView companyName_tv, userName_tv, password_tv, password2_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);

        back_btn = (Button) findViewById(R.id.companyRegistration_back_btn);
        companyName_tv = (TextView) findViewById(R.id.companyRegistration_name_tv);
        userName_tv = (TextView) findViewById(R.id.companyRegistration_adminName_tv);
        create_btn = (Button) findViewById(R.id.companyRegistration_createuser_btn);
        password_tv = (TextView) findViewById(R.id.companyRegistration_createpassword_tv);
        password2_tv = (TextView) findViewById(R.id.companyRegistration_confirmpass_tv);

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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllRequiredFieldsFilled()){
                    final String companyName = companyName_tv.getText().toString();
                    final String password = password2_tv.getText().toString();

                    String url = getString(R.string.baseURL) + getString(R.string.companiesURL) + "register";

                    StringRequest registerCompany = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("blabla", response);

                                    String userName = userName_tv.getText().toString();
                                    if(!response.equals("-1")) {
                                        try {
                                            JSONObject user = new JSONObject();

                                            user.put("Username", userName);
                                            user.put("Password", password);
                                            user.put("CompanyId", response);
                                            NewUser.createUser(getApplicationContext(), user);

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Company " + companyName + " and user " + userName + " successfully registered", Toast.LENGTH_LONG).show();
                                        }catch (JSONException ex) { Log.e("JSONException", ex.toString()); }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Error creating company", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                        }
                    }){
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            JSONObject company = new JSONObject();
                            try{
                                company.put("CompanyName", companyName);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                            return company.toString().getBytes();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };

                    Singleton.getInstance(getApplicationContext()).addToRequestQueue(registerCompany);

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
        }catch(Error error){return false;}

    }
}
