package com.github.tothc.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.adapter.TodoListRecyclerViewAdapter;
import com.github.tothc.todolist.dal.TodoRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveTodoListFragment extends Fragment {

    private View view;
    private TodoRepository todoRepository;

    @BindView(R.id.todo_list)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.todo_detail_container)
    View detailContainer;

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

        todoRepository = new TodoRepository();
        setupRecyclerView();

        return view;
    }


    private void setupRecyclerView() {
        recyclerView.setAdapter(new TodoListRecyclerViewAdapter(todoRepository.getAllActiveTodo(), isTwoPane()));
    }

    private boolean isTwoPane() {
        return detailContainer != null;
    }

}
