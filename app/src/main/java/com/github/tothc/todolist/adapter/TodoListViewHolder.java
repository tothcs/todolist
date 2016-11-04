package com.github.tothc.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.tothc.todolist.model.TodoListItem;

public class TodoListViewHolder extends RecyclerView.ViewHolder {

    private TodoListItem todoListItem;

    public TodoListViewHolder(View itemView) {
        super(itemView);
    }
}
