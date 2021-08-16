package com.example.transporte_pay.data.response;

public class AuthResponse {
    private int id;
    private int role_id;
    private String name;
    private String email;
    private Integer google_id;
    private int token;

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

    public Integer getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(Integer google_id) {
        this.google_id = google_id;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
