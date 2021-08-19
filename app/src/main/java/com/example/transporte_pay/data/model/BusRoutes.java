package com.example.transporte_pay.data.model;

public class BusRoutes {

    private Integer id;
    private String starting_point;
    private String destination;
    private int fare;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStarting_point() {
        return starting_point;
    }

    public void setStarting_point(String starting_point) {
        this.starting_point = starting_point;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "BusRoutes{" +
                "id=" + id +
                ", starting_point='" + starting_point + '\'' +
                ", destination='" + destination + '\'' +
                ", fare=" + fare +
                '}';
    }
}
