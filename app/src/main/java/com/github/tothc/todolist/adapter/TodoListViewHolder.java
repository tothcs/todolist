package com.github.tothc.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.model.TodoListItem;

import butterknife.BindView;

public class TodoListViewHolder extends RecyclerView.ViewHolder {

    private TodoListItem todoListItem;
    private View viewHolder;

    @BindView(R.id.listItemTitle)
    TextView titleTextView;

    public TodoListViewHolder(View itemView) {
        super(itemView);
        viewHolder = itemView;
    }

    public TodoListItem getTodoListItem() {
        return todoListItem;
    }

    public void setTodoListItem(TodoListItem todoListItem) {
        this.todoListItem = todoListItem;
        titleTextView.setText(todoListItem.getTitle());
    }

    public View getViewHolder() {
        return viewHolder;
    }

}
