package com.github.tothc.todolist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.dal.TodoRepository;
import com.github.tothc.todolist.model.TodoListItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayTodoFragment extends Fragment {

    private static final String TODO_ID = "ID";

    @BindView(R.id.todo_detail)
    TextView detailText;

    public DisplayTodoFragment() {

    }

    public static DisplayTodoFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(TODO_ID, id);
        DisplayTodoFragment fragment = new DisplayTodoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todo_detail, container, false);
        ButterKnife.bind(this, rootView);
        TodoListItem todoItem = TodoRepository.getInstance().getTodoById(getArguments().getInt(TODO_ID));
        renderTodoItem(todoItem);
        return rootView;
    }

    void renderTodoItem(TodoListItem todoListItem) {
        detailText.setText(todoListItem.getName());
    }
}
