package com.github.tothc.todolist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.tothc.todolist.adapter.TodoListRecyclerViewAdapter;
import com.github.tothc.todolist.dal.TodoRepository;

public class TodoListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private TodoRepository todoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        todoRepository = new TodoRepository();

        View recyclerView = findViewById(R.id.todo_list);
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.todo_detail_container) != null) {
            mTwoPane = true;
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
        recyclerView.setAdapter(new TodoListRecyclerViewAdapter(todoRepository.getAllTodo(), mTwoPane));
    }

}
