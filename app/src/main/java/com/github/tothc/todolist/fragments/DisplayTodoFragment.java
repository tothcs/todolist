package com.github.tothc.todolist.fragments;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoCheckedTextView;
import com.github.tothc.todolist.R;
import com.github.tothc.todolist.TodoService;
import com.github.tothc.todolist.dal.WeatherInteractor;
import com.github.tothc.todolist.dal.weather.List;
import com.github.tothc.todolist.helper.DateTimeHelper;
import com.github.tothc.todolist.model.TodoListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class DisplayTodoFragment extends Fragment {

    private static final String TODO_ID = "ID";

    @BindView(R.id.display_todo_title)
    TextView todoTitle;
    @BindView(R.id.display_todo_description)
    TextView todoDescription;
    @BindView(R.id.display_todo_address)
    TextView todoAddress;
    @BindView(R.id.display_todo_start_time)
    TextView todoStartTime;
    @BindView(R.id.display_todo_estimated_time)
    TextView todoEstimatedTime;
    @BindView(R.id.display_todo_measured_time)
    TextView todoMeasuredTime;
    @BindView(R.id.display_todo_status)
    TextView todoStatus;
    @BindView(R.id.display_todo_weather)
    TextView todoWeather;
    @BindView(R.id.display_todo_show_floating_window)
    CheckBox todoShowFloatingWindow;

    @BindView(R.id.display_todo_measured_time_container)
    View todoMeasuredTimeContainer;

    private WeatherInteractor weatherInteractor;
    private TodoListItem todoItem;

    public DisplayTodoFragment() {
        weatherInteractor = new WeatherInteractor();
    }

    public static DisplayTodoFragment newInstance(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(TODO_ID, id);
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
        View rootView = inflater.inflate(R.layout.frament_display_todo, container, false);
        ButterKnife.bind(this, rootView);
        todoItem = TodoListItem.findById(TodoListItem.class, getArguments().getLong(TODO_ID));
        renderTodoItem(todoItem);
        return rootView;
    }

    @OnClick(R.id.display_todo_show_floating_window)
    public void onShowTodoFloatingWindowChange() {
        Intent intent = new Intent(getContext(), TodoService.class);
        intent.putExtra("TODO_ID", todoItem.getId().intValue());
        if (todoShowFloatingWindow.isChecked()) {
            todoItem.setFloatingViewVisible(true);
            getContext().startService(intent);
        } else {
            todoItem.setFloatingViewVisible(false);
            getContext().stopService(intent);
        }
        todoItem.save();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void renderTodoItem(TodoListItem todoListItem) {
        todoTitle.setText(todoListItem.getName());
        todoDescription.setText(todoListItem.getDescription());
        todoAddress.setText(todoListItem.getAddress());
        todoStartTime.setText(new DateTime(todoListItem.getStartingDate()).toString(DateTimeHelper.getFormatter()));
        todoEstimatedTime.setText(String.valueOf(todoListItem.getEstimatedDuration()));
        if (todoListItem.isCompleted()) {
            todoMeasuredTime.setText(String.valueOf(todoListItem.getMeasuredDuration()));
            todoStatus.setText(R.string.done_text);
        } else {
            todoMeasuredTimeContainer.setVisibility(View.GONE);
            todoStatus.setText(R.string.active_text);
        }
        todoShowFloatingWindow.setChecked(todoListItem.isFloatingViewVisible() != null ? todoListItem.isFloatingViewVisible() : false);
    }

    @OnClick(R.id.display_todo_weather_button)
    public void onDisplayWeatherButtonClick() {
        weatherInteractor.getWeatherData(todoItem.getLatitude(), todoItem.getLongitude(), DateTimeHelper.createDateTime(todoItem.getStartingDate()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayWeatherResponse(List time) {
        todoWeather.setText(time.getWeather()[0].getDescription() + ", " + time.getTemp().getDay() + " \u2103");
    }

}