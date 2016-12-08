package com.github.tothc.todolist.model;

import com.orm.SugarRecord;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class TodoListItem extends SugarRecord implements Serializable {

    private String name;
    private String description;
    private Date startingDate;
    private int estimatedDuration;
    private int measuredDuration;
    private Boolean isCompleted;
    private double latitude;
    private double longitude;
    private String address;
    private Boolean floatingViewVisible;

    public TodoListItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getMeasuredDuration() {
        return measuredDuration;
    }

    public void setMeasuredDuration(int measuredDuration) {
        this.measuredDuration = measuredDuration;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isFloatingViewVisible() {
        return floatingViewVisible;
    }

    public void setFloatingViewVisible(Boolean floatingViewVisible) {
        this.floatingViewVisible = floatingViewVisible;
    }
}
