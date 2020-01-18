package com.example.sportivo.login_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Singleton;
import com.example.sportivo.TokenManager;
import com.example.sportivo.newuser_screen.NewUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView register_tv, username_tv, password_tv;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ls_activity_main);

        register_tv = (TextView) findViewById(R.id.newuser_tv);
        login_btn = (Button) findViewById(R.id.login_btn);
        password_tv = (TextView) findViewById(R.id.password_tv);
        username_tv = (TextView) findViewById(R.id.user_tv);

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewUser.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckUsernameAndPasswordFilled()){
                    String loginURL = getString(R.string.baseURL) + getString(R.string.userURL) + "login";
                    JsonObjectRequest loginRequest = new JsonObjectRequest(StringRequest.Method.POST, loginURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                            String token = response.optString("token", "failed to get token");
                            TokenManager.setToken(token);
                            boolean decodeToken = TokenManager.decodeToken(token);
                            if(decodeToken){
                                //Toast.makeText(getApplicationContext(), TokenManager.getPayloadData("username"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), com.example.sportivo.start_screen.MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Failed to decode authentication token", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"username or password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        public byte[] getBody() {
                            JSONObject user = new JSONObject();
                            try{
                                user.put("Username", username_tv.getText().toString());
                                user.put("Password", password_tv.getText().toString());
                            } catch(JSONException e){ e.printStackTrace(); }

                            return user.toString().getBytes();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };

                    Singleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);
                }
            }
        });
    }

    private boolean CheckUsernameAndPasswordFilled(){
        if(username_tv.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "username field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password_tv.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "password field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
