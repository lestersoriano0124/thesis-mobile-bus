package com.example.transporte_pay.data.request;

import com.google.gson.annotations.SerializedName;

public class TransactionRequest {
    @SerializedName("schedule_id")
    private int scheduleID;
    private  int quantity;

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
