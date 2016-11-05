package com.github.tothc.todolist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.tothc.todolist.fragments.ActiveTodoListFragment;
import com.github.tothc.todolist.fragments.DoneTodoListFragment;

public class TodoListPagerAdapter extends FragmentStatePagerAdapter {

    private final int numberOfPages;

    public TodoListPagerAdapter(FragmentManager manager, int numberOfPages) {
        super(manager);
        this.numberOfPages = numberOfPages;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ActiveTodoListFragment();
            case 1: return new DoneTodoListFragment();
            default: return new ActiveTodoListFragment();
        }
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }
}
