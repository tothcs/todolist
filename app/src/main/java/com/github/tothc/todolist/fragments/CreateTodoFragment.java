package com.github.tothc.todolist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.dal.TodoRepository;
import com.github.tothc.todolist.model.TodoListItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTodoFragment extends Fragment {

    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;
    @BindView(R.id.estimated_time_number_picker)
    EditText estimatedTimeNumber;
    @BindView(R.id.completed_checkbox)
    CheckBox completedCheckbox;

    public CreateTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static CreateTodoFragment newInstance() {
        return new CreateTodoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_todo, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.save_created_todo)
    void onSaveCreatedTodoButtonClick() {
        TodoListItem todoListItem = new TodoListItem();
        todoListItem.setName(nameEditText.getText().toString());
        todoListItem.setDescription(descriptionEditText.getText().toString());
        todoListItem.setEstimatedDuration(Integer.valueOf(estimatedTimeNumber.getText().toString()));
        todoListItem.setCompleted(completedCheckbox.isChecked());
        TodoRepository.getInstance().persistTodo(todoListItem);
    }

}
