package com.github.tothc.todolist.model;

import com.orm.SugarRecord;

import org.joda.time.DateTime;

import java.io.Serializable;

public class TodoListItem extends SugarRecord implements Serializable {

    private String name;
    private String description;
    private TodoPosition todoPosition;
    //private DateTime startingDate;
    private int estimatedDuration;
    private int measuredDuration;
    private Boolean isCompleted;

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

    public TodoPosition getTodoPosition() {
        return todoPosition;
    }

    public void setTodoPosition(TodoPosition todoPosition) {
        this.todoPosition = todoPosition;
    }

    /*public DateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(DateTime startingDate) {
        this.startingDate = startingDate;
    }*/

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
}
