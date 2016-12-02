package com.github.tothc.todolist.events;

public class TodoItemEvent {

    private Long id;
    private TodoItemEventType todoItemEventType;

    public TodoItemEvent(TodoItemEventType todoItemEventType) {
        this.todoItemEventType = todoItemEventType;
    }

    public TodoItemEvent(Long id, TodoItemEventType todoItemEventType) {
        this.id = id;
        this.todoItemEventType = todoItemEventType;
    }

    public Long getId() {
        return id;
    }

    public TodoItemEventType getTodoItemEventType() {
        return todoItemEventType;
    }

}