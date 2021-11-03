package com.example.transporte_pay.data.request;

public class ConductorRequest {

    private String userId;
    private String bookingId;
    private String busId;
    private String longitude;
    private String latitude;
    private boolean status;
    private String remarks;
    private String health;


    public boolean isStatus(){
        return status;
    }
    public String getRemarks(){
        return remarks;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

}
