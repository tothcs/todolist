package com.github.tothc.todolist.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tothc.todolist.AlarmReceiver;
import com.github.tothc.todolist.R;
import com.github.tothc.todolist.events.TodoItemEventType;
import com.github.tothc.todolist.helper.DateTimeHelper;
import com.github.tothc.todolist.model.TodoListItem;
import com.github.tothc.todolist.model.TodoPosition;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CreateTodoFragment extends Fragment implements DateTimePickerDialogFragment.IDatePickerDialogFragment, Validator.ValidationListener {

    private static final int PLACE_PICKER_FLAG = 1;

    @NotEmpty
    @BindView(R.id.name_edit_text)
    EditText nameEditText;

    @NotEmpty
    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;

    @NotEmpty
    @BindView(R.id.estimated_time_number_picker)
    EditText estimatedTimeNumber;
    @BindView(R.id.completed_checkbox)
    CheckBox completedCheckbox;
    @BindView(R.id.create_todo_position)
    TextView createTodoPosition;
    @BindView(R.id.create_todo_start_time)
    TextView todoStartTime;

    private TodoPosition todoPosition;
    private DateTime todoItemStartingDate;
    private Validator validator;

    public CreateTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
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
        validator.put(todoStartTime, new QuickRule<TextView>() {
            @Override
            public boolean isValid(TextView view) {
                return todoItemStartingDate != null;
            }

            @Override
            public String getMessage(Context context) {
                return "Todo starting date is required";
            }
        });

        validator.validate();
    }

    @OnClick(R.id.create_todo_change_position)
    public void onChangePositionButtonClick() {
        try {
            startActivityForResult(new PlacePicker.IntentBuilder().build(getActivity()), PLACE_PICKER_FLAG);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.create_todo_change_start_time_button)
    public void onChangeStartTimeButtonClick() {
        DateTimePickerDialogFragment dateTimePickerDialogFragment = new DateTimePickerDialogFragment();
        dateTimePickerDialogFragment.setTargetFragment(this, 1);
        dateTimePickerDialogFragment.show(getFragmentManager(), "DATETIMEPICKER");
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

    @Override
    public void onDateTimeSelected(DateTime dateTime) {
        todoItemStartingDate = dateTime;
        todoStartTime.setText(dateTime.toString(DateTimeHelper.getFormatter()));
    }

    @Override
    public void onValidationSucceeded() {
        TodoListItem todoListItem = new TodoListItem();
        todoListItem.setName(nameEditText.getText().toString());
        todoListItem.setDescription(descriptionEditText.getText().toString());
        todoListItem.setEstimatedDuration(Integer.valueOf(estimatedTimeNumber.getText().toString()));
        todoListItem.setStartingDate(todoItemStartingDate.toDate());
        todoListItem.setCompleted(completedCheckbox.isChecked());

        todoListItem.save();

        if (!todoListItem.isCompleted()) {
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, todoItemStartingDate.plusMonths(1).minusMinutes(5).getMillis(),
                    PendingIntent.getBroadcast(getContext(), todoListItem.getId().intValue(), new Intent(getContext(), AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT));
        }

        EventBus.getDefault().post(TodoItemEventType.LIST_TODOS);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
