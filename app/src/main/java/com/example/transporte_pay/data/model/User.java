package com.example.transporte_pay.data.model;

import android.content.SharedPreferences;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private int role_id;
    private String name;
    private String email;
    private String google_id;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getToken() {
        return token;
    }



    public void setToken(String token) {
        this.token = token;
    }
}
