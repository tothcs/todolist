package com.github.tothc.todolist.dal;

import android.content.Context;

import com.github.tothc.todolist.model.TodoListItem;

import java.sql.SQLException;
import java.util.List;

public class TodoRepository {

    private static TodoRepository instance = null;
    private DatabaseHelper helper;

    private TodoRepository(Context context) {
        helper = new DatabaseHelper(context);
        TodoListItem todoListItem = new TodoListItem();
        todoListItem.setDescription("B");
        todoListItem.setCompleted(true);
        todoListItem.setName("AAA" + System.currentTimeMillis());
        try {
            helper.getTodoListItemDao().create(todoListItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initRepository(Context context) {
        instance = new TodoRepository(context);
    }

    public static TodoRepository getInstance() {

        return instance;
    }

    public List<TodoListItem> getAllActiveTodo() {
        try {
            return helper.getTodoListItemDao().queryForEq("isCompleted", false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TodoListItem> getAllDoneTodo() {
        try {
            return helper.getTodoListItemDao().queryForEq("isCompleted", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TodoListItem getTodoById(int id) {
        try {
            return helper.getTodoListItemDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void persistTodo(TodoListItem item) {
    }
}
