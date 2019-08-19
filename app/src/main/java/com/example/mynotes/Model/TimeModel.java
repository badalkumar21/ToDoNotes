package com.example.mynotes.Model;

public class TimeModel {

    int min;
    int hr;
    String time;

    public TimeModel(int min, int hr, String time) {
        this.min = min;
        this.hr = hr;
        this.time = time;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
