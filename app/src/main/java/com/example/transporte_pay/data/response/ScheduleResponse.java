package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScheduleResponse {
    @SerializedName("schedule")
    ArrayList<Schedule> mSchedule;

    public ArrayList<Schedule> getSchedule() {
        return mSchedule;
    }
    public void setSchedule(ArrayList<Schedule> mSchedule) {
        this.mSchedule = mSchedule;
    }
}
