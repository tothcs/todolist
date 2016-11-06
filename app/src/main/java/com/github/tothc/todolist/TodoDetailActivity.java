package com.github.tothc.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.github.tothc.todolist.events.TodoItemEventType;
import com.github.tothc.todolist.fragments.CreateTodoFragment;
import com.github.tothc.todolist.fragments.DisplayTodoFragment;
import com.github.tothc.todolist.fragments.ModifyTodoFragment;

import butterknife.BindView;

public class TodoDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle navigationDetails = getIntent().getBundleExtra("navigationDetails");
        int todoId = navigationDetails.getInt("id");
        TodoItemEventType navigationEvent = TodoItemEventType.getEventByIntValue(navigationDetails.getInt("type"));
        handleNavigationEvent(todoId, navigationEvent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, TodoListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleNavigationEvent(int id, TodoItemEventType todoItemEventType) {
        switch (todoItemEventType) {
            case DISPLAY:
                navigateToDisplayTodo(id); break;
            case MODIFY:
                navigateToModifyTodo(id); break;
            case CREATE:
                navigateToCreateTodo(); break;
            default:
                throw new UnsupportedOperationException("Navigation Event type not supported!");
        }
    }

    private void navigateToDisplayTodo(int id) {
        addFragment(DisplayTodoFragment.newInstance(id));
    }

    private void navigateToModifyTodo(int id) {
        addFragment(ModifyTodoFragment.newInstance(id));
    }

    private void navigateToCreateTodo() {
        addFragment(CreateTodoFragment.newInstance());
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.todo_detail_container, fragment).commit();
    }
}
