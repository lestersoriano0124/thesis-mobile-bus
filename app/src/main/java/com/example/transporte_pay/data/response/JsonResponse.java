package com.example.transporte_pay.data.response;

import android.provider.ContactsContract;

import com.example.transporte_pay.data.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JsonResponse {
    @SerializedName("data")
    @Expose
    private List<User> userList = new ArrayList<>();

    public List<User> getDataList() {
        return userList;
    }
}
