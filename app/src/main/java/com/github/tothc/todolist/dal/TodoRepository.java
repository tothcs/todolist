package com.github.tothc.todolist.dal;

import com.github.tothc.todolist.model.TodoListItem;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class TodoRepository {

    private static final TodoRepository instance = new TodoRepository();

    private TodoRepository() {}

    public static TodoRepository getInstance() {
        return instance;
    }

    public List<TodoListItem> getAllActiveTodo() {
        return Select.from(TodoListItem.class).where(Condition.prop("is_completed").eq(0)).list();
        //return Select.from(TodoListItem.class).where(.list();
    }

    public List<TodoListItem> getAllDoneTodo() {
        return Select.from(TodoListItem.class).where(Condition.prop("is_completed").eq(1)).list();
    }

    public boolean deleteTodoById(Long id) {
        return TodoListItem.findById(TodoListItem.class, id).delete();
    }
}
