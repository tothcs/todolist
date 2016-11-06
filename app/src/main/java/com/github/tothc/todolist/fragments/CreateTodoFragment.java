package com.github.tothc.todolist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.tothc.todolist.R;
import com.github.tothc.todolist.dal.TodoRepository;
import com.github.tothc.todolist.model.TodoListItem;
import com.github.tothc.todolist.model.TodoPosition;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CreateTodoFragment extends Fragment {

    private static final int PLACE_PICKER_FLAG = 1;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;
    @BindView(R.id.estimated_time_number_picker)
    EditText estimatedTimeNumber;
    @BindView(R.id.completed_checkbox)
    CheckBox completedCheckbox;
    @BindView(R.id.create_todo_position)
    TextView createTodoPosition;

    private TodoPosition todoPosition;

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
        todoListItem.setTodoPosition(todoPosition);
        TodoRepository.getInstance().persistTodo(todoListItem);
    }

    @OnClick(R.id.create_todo_change_position)
    void onChangePositionButtonClick() {
        try {
            startActivityForResult(new PlacePicker.IntentBuilder().build(getActivity()), PLACE_PICKER_FLAG);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PLACE_PICKER_FLAG) {
            Place place = PlacePicker.getPlace(this.getContext(), data);
            createTodoPosition.setText(place.getAddress());
            todoPosition = new TodoPosition();
            todoPosition.setLatitude(place.getLatLng().latitude);
            todoPosition.setLongitude(place.getLatLng().longitude);
            todoPosition.setAddress(place.getAddress().toString());
        }
    }
}
