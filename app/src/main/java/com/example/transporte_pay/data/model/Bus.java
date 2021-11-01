package com.example.transporte_pay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bus {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plate_number")
    @Expose
    private String plateNumber;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("gcash_number")
    @Expose
    private String gcashNumber;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude ")
    @Expose
    private String latitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setGcashNumber(String updatedAt) {
        this.gcashNumber = gcashNumber;
    }

    public String getGcashNumber() {
        return gcashNumber;
    }


    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }


    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLatitude() {
        return latitude;
    }

}
