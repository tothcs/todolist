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

public class DoneTodoListFragment extends Fragment {

    private View view;

    @BindView(R.id.todo_list)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.todo_detail_container)
    View detailContainer;

    TodoListRecyclerViewAdapter todoListRecyclerViewAdapter;

    public DoneTodoListFragment() {
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

        TodoRepository.initRepository(view.getContext());
        setupRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        todoListRecyclerViewAdapter.refreshTodoList(TodoRepository.getInstance().getAllDoneTodo());
    }

    private void setupRecyclerView() {
        todoListRecyclerViewAdapter = new TodoListRecyclerViewAdapter(TodoRepository.getInstance().getAllDoneTodo(), isTwoPane());
        recyclerView.setAdapter(todoListRecyclerViewAdapter);
    }

    private boolean isTwoPane() {
        return detailContainer != null;
    }
}
