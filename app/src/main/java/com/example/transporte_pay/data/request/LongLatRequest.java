package com.example.transporte_pay.data.request;

public class LongLatRequest {
    private boolean status;
    private String remarks;
    private String longitude;
    private String latitude;
    private String busId;

    public boolean isStatus(){
        return status;
    }
    public String getRemarks(){
        return remarks;
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

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }
}
