package com.danieldickeytodosharedproject1.phoneapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.danieldickeytodosharedproject1.api.viewmodels.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class TodoListFragment extends Fragment {
    private TodoViewModel todoViewModel;


    public TodoListFragment() {
        super(R.layout.fragment_todo_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoViewModel = new ViewModelProvider(getActivity()).get(TodoViewModel.class);

        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).show();

        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_id);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TodoAdapter todoAdapter = new TodoAdapter(todoViewModel.getTodoItems(),
                (todoThing) -> {

                    Bundle myBundle = new Bundle();
                    myBundle.putString("id", todoThing.id);
                    myBundle.putString("task", todoThing.task);
                    myBundle.putBoolean("complete", todoThing.complete);
                    System.out.println("before: " + todoThing.complete);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            // where to add, class of fragment to add, optional arguments:null
                            .replace(R.id.fragment_cotainer, EditTodoFragment.class, myBundle)
                            // docs say to do this... so you can reorder them...
                            .setReorderingAllowed(true)
                            // allows me to come back here
                            .addToBackStack(null)
                            // commit it
                            .commit();
                });
        myRecyclerView.setAdapter(todoAdapter);

        // add new item by opening edit screen
        getActivity().findViewById(R.id.add_new_task_fab).setOnClickListener(v -> {
            // add a new task for us to edit on a new fragment
            todoViewModel.addTodoItem("blank", false);
            // fragment manager
            getActivity().getSupportFragmentManager().beginTransaction()
                    // where to add, class of fragment to add, optional arguments:null
                    .replace(R.id.fragment_cotainer, EditTodoFragment.class, null)
                    // docs say to do this... so you can reorder them...
                    .setReorderingAllowed(true)
                    // allows me to come back here
                    .addToBackStack(null)
                    // commit it
                    .commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).show();
    }
}