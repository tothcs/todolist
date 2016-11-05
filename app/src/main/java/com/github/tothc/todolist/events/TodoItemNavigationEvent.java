package com.github.tothc.todolist.events;

public class TodoItemNavigationEvent {

    private int id;
    private NavigationEventType navigationEventType;

    public TodoItemNavigationEvent(NavigationEventType navigationEventType) {
        this.navigationEventType = navigationEventType;
    }

    public TodoItemNavigationEvent(int id, NavigationEventType navigationEventType) {
        this.id = id;
        this.navigationEventType = navigationEventType;
    }

    public int getId() {
        return id;
    }

    public NavigationEventType getNavigationEventType() {
        return navigationEventType;
    }

}