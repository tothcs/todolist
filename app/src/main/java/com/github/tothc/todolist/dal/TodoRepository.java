package com.github.tothc.todolist.dal;

import com.github.tothc.todolist.model.TodoListItem;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {

    public List<TodoListItem> getAllTodo() {
        List<TodoListItem> todoList = new ArrayList<TodoListItem>();
        for(int i = 0; i < 20; i++) {
            TodoListItem item = new TodoListItem();
            item.setTitle("ABCD" + i);

            todoList.add(item);
        }

        return getAllTodo();
    }
}
