package com.github.tothc.todolist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.TodoDetailActivity;
import com.github.tothc.todolist.events.NavigationEventType;
import com.github.tothc.todolist.events.TodoItemNavigationEvent;
import com.github.tothc.todolist.model.TodoListItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.listItemTitle)
    TextView titleTextView;
    @BindView(R.id.listItemStartDate)
    TextView startDateTextView;

    private TodoListItem todoListItem;
    private View view;

    public TodoListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.view = view;
    }

    public void setTodoListItem(TodoListItem todoListItem) {
        this.todoListItem = todoListItem;
        titleTextView.setText(todoListItem.getName());
        startDateTextView.setText("asd");
    }

    @OnClick
    void onClickOnTodoItem() {
        EventBus.getDefault().post(new TodoItemNavigationEvent(todoListItem.getId(), NavigationEventType.DISPLAY));
    }

}
