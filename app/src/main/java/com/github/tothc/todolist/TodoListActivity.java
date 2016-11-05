package com.github.tothc.todolist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.tothc.todolist.adapter.TodoListRecyclerViewAdapter;
import com.github.tothc.todolist.dal.TodoRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoListActivity extends AppCompatActivity {

    private TodoRepository todoRepository;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.todo_list)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.todo_detail_container)
    View detailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        todoRepository = new TodoRepository();
        recyclerView.setAdapter(new TodoListRecyclerViewAdapter(todoRepository.getAllTodo(), isTwoPane()));
    }

    private boolean isTwoPane() {
        return detailContainer != null;
    }

}
