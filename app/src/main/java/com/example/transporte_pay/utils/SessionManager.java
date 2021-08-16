package com.example.transporte_pay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SessionManager {

    // Shared Preferences
    private static SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    public Context _context;
    // Shared pref mode
    public int PRIVATE_MODE = 0;
    // Shared pref file name
    private static final String PREF_NAME = "LOGIN";
    // All Shared Preferences Keys
    public static final String PREF_USER_TOKEN = "user_token";
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name (make variable public to access from outside)
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String ROLE = "ROLE";
    public static final String PASS = "PASS";
    public static final String G_ID = "G_ID";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSession(String name, String email, Integer role, Integer googleID){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(ROLE, String.valueOf(role));
        editor.putString(G_ID, String.valueOf(googleID));

        editor.apply();
    }

//    Create login Session
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public HashMap<String, String> getUSerDetails(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME, pref.getString(NAME, null));
        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(ROLE, pref.getString(ROLE, null));
        user.put(PASS, pref.getString(PASS, null));
        user.put(G_ID, pref.getString(G_ID, null));

        return user;
    }



    public static void saveAuthToken(int token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_USER_TOKEN, String.valueOf(token));
        editor.apply();
    }


    public String fetchAuthToken(){
        return pref.getString(PREF_USER_TOKEN, null);
    }


}

