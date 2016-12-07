package com.github.tothc.todolist.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tothc.todolist.AlarmReceiver;
import com.github.tothc.todolist.R;
import com.github.tothc.todolist.adapter.TodoListRecyclerViewAdapter;
import com.github.tothc.todolist.dal.TodoRepository;
import com.github.tothc.todolist.events.TodoItemEvent;
import com.github.tothc.todolist.events.TodoItemEventType;
import com.github.tothc.todolist.model.TodoListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveTodoListFragment extends Fragment {

    private View view;

    @BindView(R.id.todo_list)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.todo_detail_container)
    View detailContainer;

    private TodoListRecyclerViewAdapter todoListRecyclerViewAdapter;

    public ActiveTodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        todoListRecyclerViewAdapter.refreshTodoList(TodoRepository.getInstance().getAllActiveTodo());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    private void setupRecyclerView() {
        todoListRecyclerViewAdapter = new TodoListRecyclerViewAdapter(TodoRepository.getInstance().getAllActiveTodo(), isTwoPane());
        recyclerView.setAdapter(todoListRecyclerViewAdapter);
    }

    private boolean isTwoPane() {
        return detailContainer != null;
    }


    @Subscribe
    public void onTodoItemEvent(TodoItemEvent todoItemEvent) {
        TodoListItem todoListItem = todoItemEvent.getTodoListItem();
        if (todoListItem.isCompleted()) {
            return;
        }

        if (todoItemEvent.getTodoItemEventType() == TodoItemEventType.DELETE) {
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(PendingIntent.getBroadcast(getContext(), todoListItem.getId().intValue(), new Intent(getContext(), AlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT));
            todoListItem.delete();
            todoListRecyclerViewAdapter.refreshTodoList(TodoRepository.getInstance().getAllDoneTodo());
        }
    }

}
