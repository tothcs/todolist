package com.github.tothc.todolist;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import com.github.tothc.todolist.model.TodoListItem;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int todoListItemId = intent.getIntExtra("TODO_ID", -1);

        if (todoListItemId != -1) {
            TodoListItem todoListItem = TodoListItem.findById(TodoListItem.class, todoListItemId);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(todoListItemId, createTodoNotification(context, todoListItem));
        }
    }

    private Notification createTodoNotification(Context context, TodoListItem todoListItem) {
        return new NotificationCompat.Builder(context)
                .setContentTitle("Todo notification")
                .setContentText(todoListItem.getName() + " event will start in 5 minutes.")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setAutoCancel(false)
                .setVibrate(new long[] {500, 500, 1000, 2000})
                .build();
    }
}