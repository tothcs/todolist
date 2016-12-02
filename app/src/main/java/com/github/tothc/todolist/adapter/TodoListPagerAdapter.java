package com.github.tothc.todolist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.tothc.todolist.fragments.ActiveTodoListFragment;
import com.github.tothc.todolist.fragments.DoneTodoListFragment;

public class TodoListPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUMBER_OF_PAGES = 2;
    private static final String[] PAGE_NAMES = {"Active", "Done"};

    public TodoListPagerAdapter(FragmentManager manager) {
        super(manager);
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
        return NUMBER_OF_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_NAMES[position];
    }
}
