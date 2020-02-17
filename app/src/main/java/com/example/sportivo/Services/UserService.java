package com.example.sportivo.Services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportivo.R;
import com.example.sportivo.admin_screen.AdminMainActivity;
import com.example.sportivo.admin_screen.AdminReservationsDataStorage;
import com.example.sportivo.login_screen.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService {

    public static void login(final Context context, final String username, final String password) {
        String loginURL = context.getString(R.string.baseURL) + context.getString(R.string.userURL) + "login";
        JsonObjectRequest loginRequest = new JsonObjectRequest(StringRequest.Method.POST, loginURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                String token = response.optString("token", "failed to get token");
                int companyId = response.optInt("companyId", -1);
                AuthTokenService.setToken(token);
                Log.i("blabla", String.valueOf(companyId));

                if(AuthTokenService.decodeToken(token)){
                    //Toast.makeText(getApplicationContext(), AuthTokenService.getPayloadData("username"), Toast.LENGTH_LONG).show();
                    Intent intent;
                    if(companyId == -1) {
                        intent = new Intent(context.getApplicationContext(), com.example.sportivo.start_screen.MainActivity.class);
                        ReservationService.isAdmin = false;
                    }else{
                        AdminReservationsDataStorage.companyId=companyId;
                        intent = new Intent(context.getApplicationContext(), AdminMainActivity.class);
                        ReservationService.isAdmin = true;
                    }
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context.getApplicationContext(), "Failed to decode authentication token", Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context.getApplicationContext(),"username or password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public byte[] getBody() {
                JSONObject user = new JSONObject();
                try{
                    user.put("Username", username);
                    user.put("Password", password);
                } catch(JSONException e){ e.printStackTrace(); }

                return user.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Volley.newRequestQueue(context).add(loginRequest);
    }

    public static void createUser(final Context context, final JSONObject user){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.userURL) + "register";

        StringRequest registerUser = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "User " + user.optString("Username", "") + " successfully registered", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                return user.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Volley.newRequestQueue(context).add(registerUser);
    }

    public static void createCompany(final Context context, final String companyName, final String userName, final String password, final int startHour, final int endHour){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.companiesURL) + "register";

        StringRequest registerCompany = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("blabla", response);

                        if(!response.equals("-1")) {
                            try {
                                JSONObject user = new JSONObject();

                                user.put("Username", userName);
                                user.put("Password", password);
                                user.put("CompanyId", response);
                                UserService.createUser(context.getApplicationContext(), user);

                                Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                                context.startActivity(intent);
                                Toast.makeText(context.getApplicationContext(), "Company " + companyName + " and user " + userName + " successfully registered", Toast.LENGTH_LONG).show();
                            }catch (JSONException ex) { Log.e("JSONException", ex.toString()); }
                        }else{
                            Toast.makeText(context.getApplicationContext(), "Error creating company", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject company = new JSONObject();
                try{
                    company.put("CompanyName", companyName);
                    company.put("Opens", startHour);
                    company.put("Closes", endHour);
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

        Volley.newRequestQueue(context).add(registerCompany);
    }
}
