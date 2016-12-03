package com.github.tothc.todolist.events;

import com.github.tothc.todolist.model.TodoListItem;

public class TodoItemEvent {

    private TodoListItem todoListItem;
    private TodoItemEventType todoItemEventType;

    public TodoItemEvent(TodoItemEventType todoItemEventType) {
        this.todoItemEventType = todoItemEventType;
    }

    public TodoItemEvent(TodoListItem todoListItem, TodoItemEventType todoItemEventType) {
        this.todoListItem = todoListItem;
        this.todoItemEventType = todoItemEventType;
    }

    public TodoListItem getTodoListItem() { return todoListItem; }

    public TodoItemEventType getTodoItemEventType() {
        return todoItemEventType;
    }

}