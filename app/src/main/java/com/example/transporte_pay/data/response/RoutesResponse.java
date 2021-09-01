package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.Routes;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoutesResponse {
    @SerializedName("routes")
    private ArrayList<Routes> mRoutes;

    public ArrayList<Routes> getRoutes() {
        return mRoutes;
    }

    public void setRoutes(ArrayList<Routes> mRoutes) {
        this.mRoutes = mRoutes;
    }
}
