package com.github.tothc.todolist.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

@DatabaseTable
public class TodoListItem {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private TodoPosition todoPosition;
    //@DatabaseField
    //private DateTime startingDate;
    @DatabaseField
    private int estimatedDuration;
    @DatabaseField
    private int measuredDuration;
    @DatabaseField
    private boolean isCompleted;

    public TodoListItem() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
