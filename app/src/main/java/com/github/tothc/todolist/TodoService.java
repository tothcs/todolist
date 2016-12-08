package com.github.tothc.todolist;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.tothc.todolist.helper.DateTimeHelper;
import com.github.tothc.todolist.model.TodoListItem;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

public class TodoService extends Service {
    public TodoService() {
    }

    private WindowManager windowManager;
    private View floatingView;
    private TextView todoStartDate;
    private TextView todoTitle;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int todoItemId = intent.getIntExtra("TODO_ID", -1);
        if (todoItemId != -1) {
            TodoListItem todoListItem = TodoListItem.findById(TodoListItem.class, todoItemId);
            showFloatingWindow(todoListItem);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        hideFloatingWindow();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showFloatingWindow(TodoListItem todoListItem) {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        floatingView = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.todo_floating_view, null);
        todoStartDate = (TextView) floatingView.findViewById(R.id.todo_floating_view_start_date);
        todoTitle = (TextView) floatingView.findViewById(R.id.todo_floating_view_title);

        todoStartDate.setText(new DateTime(todoListItem.getStartingDate()).toString(DateTimeHelper.getShortFormatter()));
        todoTitle.setText(todoListItem.getName());

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(floatingView, params);

        try {
            floatingView.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            if (floatingView != null) {
                                windowManager.updateViewLayout(floatingView, paramsF);
                            }
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideFloatingWindow() {
        if (floatingView != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
            todoStartDate = null;
            todoTitle = null;
        }
    }
}
