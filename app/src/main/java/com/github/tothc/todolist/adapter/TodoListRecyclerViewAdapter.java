package com.github.tothc.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.dal.TodoRepository;
import com.github.tothc.todolist.model.TodoListItem;

import java.util.List;

public class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListViewHolder> {

    private List<TodoListItem> todoList;
    private boolean isTwoPane;

    public TodoListRecyclerViewAdapter(List<TodoListItem> todoList, boolean isTwoPane) {
        this.todoList = todoList;
        this.isTwoPane = isTwoPane;
    }

    @Override
    public TodoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_content, parent, false);
        return new TodoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoListViewHolder holder, int position) {
        holder.setTodoListItem(todoList.get(position));

    }

    @Override
    public int getItemCount() {
        return todoList != null ? todoList.size() : 0;
    }

    public void refreshTodoList(List<TodoListItem> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }
}
