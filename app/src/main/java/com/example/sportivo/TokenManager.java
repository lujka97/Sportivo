package com.example.sportivo;

import android.util.Log;
import android.widget.Toast;

import com.google.common.io.BaseEncoding;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenManager {

    private static JSONObject payloadJson, headerJson, signatureJson;
    private static String token;

    public static boolean decodeToken(String token){
        String[] parts = token.split("\\."); // split out the "parts" (header, payload and signature)

        String tokenHeader = new String (BaseEncoding.base64Url().decode(parts[0]));
        String tokenPayload = new String (BaseEncoding.base64Url().decode(parts[1]));
        //String tokenSignature = new String(BaseEncoding.base64Url().decode(parts[2]));

        try{
            TokenManager.headerJson = new JSONObject(tokenHeader);
            TokenManager.payloadJson = new JSONObject(tokenPayload);
            //TokenManager.signatureJson = new JSONObject(tokenSignature);
        } catch(JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static JSONObject getPayloadData(){
        return TokenManager.payloadJson;
    }

    public static String getPayloadData(String name){
        return TokenManager.payloadJson.optString(name, "failureToGet" + name);
    }

    public static void setToken(String token){
        TokenManager.token = token;
    }

}
