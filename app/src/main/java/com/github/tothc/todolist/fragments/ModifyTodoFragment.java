package com.github.tothc.todolist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tothc.todolist.R;

public class ModifyTodoFragment extends Fragment {

    private static final String TODO_ID = "ID";

    public ModifyTodoFragment() {
        // Required empty public constructor
    }

    public static ModifyTodoFragment newInstance(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(TODO_ID, id);
        ModifyTodoFragment fragment = new ModifyTodoFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_todo, container, false);
    }

}
