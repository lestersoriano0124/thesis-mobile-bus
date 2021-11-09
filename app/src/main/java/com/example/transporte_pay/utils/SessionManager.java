package com.example.transporte_pay.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.transporte_pay.views.activity.LoginActivity;

import java.util.HashMap;


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
    public static final String ID = "ID";
    public static final String STATUS = "STATUS";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void createSession(String name, String email, int role, String gID, int id,String status){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putInt(ROLE, role);
        editor.putString(G_ID, String.valueOf(gID));
        editor.putInt(ID,id);
        editor.putString(STATUS,status);
        editor.apply();
    }

    public void updateData (String name, String email){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public void updateStatus(String status){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(STATUS, status);
        editor.apply();
    }

//     * Check login method wil check user login status
//     * If false it will redirect user to login page
//     * Else won't do anything
//            * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Long Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public HashMap<String, String> getUSerDetails(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME, pref.getString(NAME, null));
        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(PASS, pref.getString(PASS, null));
        user.put(G_ID, pref.getString(G_ID, null));
        user.put(PREF_USER_TOKEN, pref.getString(PREF_USER_TOKEN, null));
        user.put(STATUS, pref.getString(STATUS, "open"));

        return user;
    }

    public HashMap<String, Integer> getID(){
        HashMap<String, Integer> user = new HashMap<>();
        user.put(ID,pref.getInt(ID, 0));
        user.put(ROLE,pref.getInt(ROLE, 0));
        return user;
    }



    public static void saveAuthToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_USER_TOKEN, token);
        editor.apply();
    }


//    public String fetchAuthToken(){
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(PREF_USER_TOKEN, pref.getString(PREF_USER_TOKEN,null));
//        return PREF_USER_TOKEN;
//    }


}

