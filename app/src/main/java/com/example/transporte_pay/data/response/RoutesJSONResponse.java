package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.BusRoutes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoutesJSONResponse {

    @SerializedName("bus_routes")
    @Expose
    private List<BusRoutes> bus_routes = null;

    //getter && setter
    public List<BusRoutes> getBus_routes() {
        return bus_routes;
    }

    public void setBus_routes(List<BusRoutes> bus_routes) {
        this.bus_routes = bus_routes;
    }
}
