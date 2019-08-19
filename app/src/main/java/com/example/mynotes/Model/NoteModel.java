package com.example.mynotes.Model;

public class NoteModel {

    private String day;
    private String date;
    private int priorityHigh;
    private int priorityMedium;
    private int priorityLow;

    public NoteModel() {
    }

    public NoteModel(String day, String date, int priorityHigh, int priorityMedium, int priorityLow) {
        this.day = day;
        this.date = date;
        this.priorityHigh = priorityHigh;
        this.priorityMedium = priorityMedium;
        this.priorityLow = priorityLow;
    }


    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public int getPriorityHigh() {
        return priorityHigh;
    }

    public int getPriorityMedium() {
        return priorityMedium;
    }

    public int getPriorityLow() {
        return priorityLow;
    }
}
