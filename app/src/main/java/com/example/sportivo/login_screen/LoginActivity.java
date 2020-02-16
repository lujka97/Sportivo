package com.example.sportivo.login_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Services.ReservationService;
import com.example.sportivo.Services.Singleton;
import com.example.sportivo.Services.TokenManager;
import com.example.sportivo.Services.UserService;
import com.example.sportivo.admin_screen.AdminMainActivity;
import com.example.sportivo.admin_screen.AdminReservationsDataStorage;
import com.example.sportivo.registration_screen.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView register_tv, username_tv, password_tv;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        register_tv = (TextView) findViewById(R.id.newuser_tv);
        login_btn = (Button) findViewById(R.id.login_btn);
        password_tv = (TextView) findViewById(R.id.password_tv);
        username_tv = (TextView) findViewById(R.id.user_tv);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_CALENDAR}, 1);
        }

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckUsernameAndPasswordFilled()){
                    String username = username_tv.getText().toString();
                    String password = password_tv.getText().toString();

                    UserService.login(getApplicationContext(), username, password);
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
