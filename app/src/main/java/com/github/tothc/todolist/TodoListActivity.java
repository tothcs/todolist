package com.github.tothc.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.tothc.todolist.adapter.TodoListPagerAdapter;
import com.github.tothc.todolist.events.NavigationEventType;
import com.github.tothc.todolist.events.TodoItemNavigationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private static final int NUMBER_OF_PAGES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);

        TodoListPagerAdapter todoListPagerAdapter = new TodoListPagerAdapter(getSupportFragmentManager(), NUMBER_OF_PAGES);
        setupToolbar();
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
    protected void onTodoItemNavigation(TodoItemNavigationEvent todoItemNavigationEvent) {
        boolean twoPane = false;
        if (twoPane) {
            // TODO: 2016. 11. 05.  Master-detail flow "tablet" view
        } else {
            navigateToDetailActivity(todoItemNavigationEvent);
        }
    }

    @OnClick(R.id.toolbar_create_new_todo_button)
    void onCreateNewTodoButtonClick() {
        navigateToDetailActivity(new TodoItemNavigationEvent(NavigationEventType.CREATE));
    }

    private void navigateToDetailActivity(TodoItemNavigationEvent todoItemNavigationEvent) {
        Intent intent = new Intent(this, TodoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", todoItemNavigationEvent.getId());
        bundle.putInt("type", todoItemNavigationEvent.getNavigationEventType().getEventTypeIntValue());
        intent.putExtra("navigationDetails", bundle);
        startActivity(intent);
    }



    private void setupToolbar() {
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
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
