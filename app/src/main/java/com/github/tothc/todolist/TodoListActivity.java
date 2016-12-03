package com.github.tothc.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.tothc.todolist.adapter.TodoListPagerAdapter;
import com.github.tothc.todolist.dal.TodoRepository;
import com.github.tothc.todolist.events.TodoItemEventType;
import com.github.tothc.todolist.events.TodoItemEvent;
import com.github.tothc.todolist.model.TodoListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoListActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);

        TodoListPagerAdapter todoListPagerAdapter = new TodoListPagerAdapter(getSupportFragmentManager());
        setupTabLayout();

        viewPager.setAdapter(todoListPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTodoItemEvent(TodoItemEvent todoItemEvent) {
        if (todoItemEvent.getTodoItemEventType() == TodoItemEventType.DELETE) {

        }  else {
            boolean twoPane = false;
            if (twoPane) {
                // TODO: 2016. 11. 05.  Master-detail flow "tablet" view
            } else {
                navigateToDetailActivity(todoItemEvent);
            }
        }
    }

    @OnClick(R.id.toolbar_create_new_todo_fab)
    public void onCreateNewTodoButtonClick() {
        navigateToDetailActivity(new TodoItemEvent(TodoItemEventType.CREATE));
    }

    private void navigateToDetailActivity(TodoItemEvent todoItemEvent) {
        Intent intent = new Intent(this, TodoDetailActivity.class);
        intent.putExtra("navigationDetails", createNavigationBundle(todoItemEvent));
        startActivity(intent);
    }

    private Bundle createNavigationBundle(TodoItemEvent todoItemEvent) {
        Bundle bundle = new Bundle();
        if (todoItemEvent.getTodoListItem() != null && todoItemEvent.getTodoListItem().getId() != null) {
            bundle.putLong("id", todoItemEvent.getTodoListItem().getId());
        }
        bundle.putInt("type", todoItemEvent.getTodoItemEventType().getEventTypeIntValue());
        return bundle;
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.TAB_ACTIVE));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.TAB_DONE));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}
