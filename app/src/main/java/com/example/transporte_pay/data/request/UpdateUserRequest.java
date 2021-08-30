package com.example.transporte_pay.data.request;

public class UpdateUserRequest {

    private int id;
    private String name;
    private String email;
    private int role_id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRole_id() {
        return role_id;
    }

    //SETTER

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
