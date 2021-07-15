package com.danieldickeytodosharedproject1.phoneapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class GroupFragment extends Fragment {

    public GroupFragment() {
        super(R.layout.fragment_group);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();
    }
}