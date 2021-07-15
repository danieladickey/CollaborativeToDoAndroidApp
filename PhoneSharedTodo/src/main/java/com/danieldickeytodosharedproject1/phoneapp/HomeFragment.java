package com.danieldickeytodosharedproject1.phoneapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.danieldickeytodosharedproject1.api.models.User;
import com.danieldickeytodosharedproject1.api.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    UserViewModel userViewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.go_somewhere_else).setOnClickListener(v -> {
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_cotainer, ScrollingDemoFragment.class, null)
//                    .setReorderingAllowed(true)
//                    .addToBackStack(null)
//                    .commit();
//        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    userViewModel.retrieveUserName();
                    // welcome message
                    TextView welcome = view.findViewById(R.id.welcomeTextView);
                    if (userViewModel.getUser().getValue().getName() != null) {
                        welcome.setText("Welcome,\n" + userViewModel.getUser().getValue().getName() + "!");
                    }
                    else {
                        welcome.setText("Welcome,\n" + user.getEmail() + "!");
                    }

                    // logout button
                    Button logoutButton = view.findViewById(R.id.home_logout_button);
                    logoutButton.setOnClickListener(v -> {
                        userViewModel.signOut();
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide(); // or .show();
    }
}