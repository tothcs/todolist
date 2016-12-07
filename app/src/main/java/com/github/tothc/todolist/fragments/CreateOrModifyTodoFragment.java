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
import com.github.tothc.todolist.constants.Flags;
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

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CreateOrModifyTodoFragment extends Fragment implements DateTimePickerDialogFragment.IDatePickerDialogFragment, Validator.ValidationListener {

    @NotEmpty
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
    @BindView(R.id.create_todo_start_time)
    TextView todoStartTime;

    private TodoListItem todoItemToModify;
    private TodoPosition todoPosition;
    private DateTime todoItemStartingDate;
    private Validator validator;
    private static final String TODO_ID = "ID";

    public CreateOrModifyTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public static CreateOrModifyTodoFragment newCreateTodoFragmentInstance() {
        return new CreateOrModifyTodoFragment();
    }

    public static CreateOrModifyTodoFragment newModifyTodoFragmentInstance(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(TODO_ID, id);
        CreateOrModifyTodoFragment fragment = new CreateOrModifyTodoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_or_modify_todo, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null && getArguments().get(TODO_ID) != null) {
            todoItemToModify = TodoListItem.findById(TodoListItem.class, getArguments().getLong(TODO_ID));
            renderTodoItemToModify();
        }

        return rootView;
    }

    private void renderTodoItemToModify() {
        nameEditText.setText(todoItemToModify.getName());
        descriptionEditText.setText(todoItemToModify.getDescription());
        estimatedTimeNumber.setText(String.valueOf(todoItemToModify.getEstimatedDuration()));
        completedCheckbox.setChecked(todoItemToModify.isCompleted());
        createTodoPosition.setText(todoItemToModify.getAddress());
        todoItemStartingDate = new DateTime(todoItemToModify.getStartingDate());
        todoStartTime.setText(todoItemStartingDate.toString(DateTimeHelper.getFormatter()));
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
            startActivityForResult(new PlacePicker.IntentBuilder().build(getActivity()), Flags.PLACE_PICKER_FLAG);
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
        if (resultCode == RESULT_OK && requestCode == Flags.PLACE_PICKER_FLAG) {
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
        boolean isModify = (todoItemToModify != null && todoItemToModify.getId() != null);
        TodoListItem todoListItem = isModify ? todoItemToModify : new TodoListItem();
        todoListItem.setName(nameEditText.getText().toString());
        todoListItem.setDescription(descriptionEditText.getText().toString());
        if (StringUtils.isNumeric(estimatedTimeNumber.getText().toString())) {
            todoListItem.setEstimatedDuration(Integer.valueOf(estimatedTimeNumber.getText().toString()));
        }
        todoListItem.setStartingDate(todoItemStartingDate.toDate());
        if (todoPosition != null) {
            todoListItem.setLatitude(todoPosition.getLatitude());
            todoListItem.setLongitude(todoPosition.getLongitude());
            todoListItem.setAddress(todoPosition.getAddress());
        }

        if (isModify && !todoItemToModify.isCompleted() && completedCheckbox.isChecked()) {
            todoListItem.setMeasuredDuration(Minutes.minutesBetween(todoItemStartingDate, DateTime.now()).getMinutes());
        }

        todoListItem.setCompleted(completedCheckbox.isChecked());

        todoListItem.save();

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmReceiverIntent = new Intent(getContext(), AlarmReceiver.class);
        if (isModify) {
            alarmManager.cancel(PendingIntent.getBroadcast(getContext(), todoListItem.getId().intValue(), alarmReceiverIntent, PendingIntent.FLAG_CANCEL_CURRENT));
        }
        if (!todoListItem.isCompleted() && todoItemStartingDate.isAfter(DateTime.now().plusMinutes(5))) {
            alarmReceiverIntent.putExtra("TODO_ID", todoListItem.getId().intValue());
            alarmManager.set(AlarmManager.RTC_WAKEUP, todoItemStartingDate.minusMinutes(5).getMillis(),
                    PendingIntent.getBroadcast(getContext(), todoListItem.getId().intValue(), alarmReceiverIntent, PendingIntent.FLAG_CANCEL_CURRENT));
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
