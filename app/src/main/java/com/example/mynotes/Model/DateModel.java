package com.example.mynotes.Model;

public class DateModel {

    private int day;
    private int month;
    private int year;
    private String date;

    public DateModel(int day, int month, int year, String date) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
