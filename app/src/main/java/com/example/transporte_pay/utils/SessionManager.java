package com.example.transporte_pay.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    // Shared Preferences
    private static SharedPreferences pref;
    // Context
    public Context _context;
    // Shared pref mode
    public int PRIVATE_MODE = 0;
    // Shared pref file name
    private static final String PREF_NAME = "Transport ePay App";
    // All Shared Preferences Keys
    public static final String PREF_USER_TOKEN = "user_token";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public static void saveAuthToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_USER_TOKEN, token);
        editor.apply();
    }


    public String fetchAuthToken(){
        return pref.getString(PREF_USER_TOKEN, null);
    }


}

