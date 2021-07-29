package com.example.transporte_pay.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private Integer id; //
    private Integer role_id; //
    private String name;
    private String email;
    @SerializedName("id")
    private String google_id;
    private Integer token; //

    public User(String name, String email, String google_id) {
        this.name = name;
        this.email = email;
        this.google_id = google_id;
    }
}
