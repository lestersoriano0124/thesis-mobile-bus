package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.Booking;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionResponse {

    @SerializedName("bookings")
    @Expose
    private List<Booking> bookings = null;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

