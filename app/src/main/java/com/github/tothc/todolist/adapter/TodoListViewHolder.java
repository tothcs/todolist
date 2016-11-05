package com.github.tothc.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.model.TodoListItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.listItemTitle)
    TextView titleTextView;
    @BindView(R.id.listItemStartDate)
    TextView startDateTextView;

    public TodoListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setTodoListItem(TodoListItem todoListItem) {
        titleTextView.setText(todoListItem.getName());
        startDateTextView.setText("asd");
    }

}
