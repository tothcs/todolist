package com.github.tothc.todolist.adapter;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.events.TodoItemEventType;
import com.github.tothc.todolist.events.TodoItemEvent;
import com.github.tothc.todolist.model.TodoListItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

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
        postTodoItemEvent(TodoItemEventType.DISPLAY);
    }

    @OnLongClick
    boolean onLongClickOnTodoItem() {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.todo_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.todo_item_menu_modify) {
                    postTodoItemEvent(TodoItemEventType.MODIFY);
                }
                else if (item.getItemId() == R.id.todo_item_menu_delete) {
                    postTodoItemEvent(TodoItemEventType.DELETE);
                }
                else {
                    throw new UnsupportedOperationException("Unexpected behaviour: not supported menu item.");
                }
                return false;
            }
        });
        popup.show();
        return false;
    }

    private void postTodoItemEvent(TodoItemEventType todoItemEventType) {
        EventBus.getDefault().post(new TodoItemEvent(todoListItem.getId(), todoItemEventType));
    }

}
