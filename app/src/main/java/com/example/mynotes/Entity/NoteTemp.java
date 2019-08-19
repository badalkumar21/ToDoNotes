package com.example.mynotes.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.mynotes.Model.DateModel;
import com.example.mynotes.Model.TimeModel;

@Entity(tableName = "note_table_temp")
public class NoteTemp {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    @ColumnInfo(name = "priority")
    private int priority;


    private int day;
    private int month;
    private int year;
    private String date;

    int min;
    int hr;
    String time;

    @Ignore
    private int others;

//    @Ignore
//    public NoteTemp(String title, String description, int priority) {
//        this.title = title;
//        this.description = description;
//        this.priority = priority;
//
//        this.day = 1;
//        this.month = 1;
//        this.year = 1;
//        this.date = "";
//        this.min = 1;
//        this.hr = 1;
//        this.time = "";
//    }


    public NoteTemp(String title, String description, int priority, int day, int month, int year, String date, int min, int hr, String time) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = date;
        this.min = min;
        this.hr = hr;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getMin() {
        return min;
    }

    public int getHr() {
        return hr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public void setMin(int min) {
        this.min = min;
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

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }
}
