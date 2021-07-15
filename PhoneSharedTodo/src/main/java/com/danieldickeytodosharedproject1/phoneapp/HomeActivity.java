package com.danieldickeytodosharedproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danieldickeytodosharedproject1.api.models.User;
import com.danieldickeytodosharedproject1.api.viewmodels.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends ActivityWithUser {
    public static final  int RECORD_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get permission to use mic
        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_CODE);

        // get drawer menu ...
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        toolbar.setNavigationOnClickListener(view -> {
            drawer.open();
        });

        // more menu stuff to choose which fragment is displayed
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            if (menuItem.getItemId() == R.id.home_item) {
                getSupportFragmentManager().beginTransaction()
                        // where to add, class of fragment to add, optional arguments:null
                        .replace(R.id.fragment_cotainer, HomeFragment.class, null)
                        // docs say to do this... so you can reorder them...
                        .setReorderingAllowed(true)
                        // commit it
                        .commit();
            }
            if (menuItem.getItemId() == R.id.settings_item) {
                getSupportFragmentManager().beginTransaction()
                        // where to add, class of fragment to add, optional arguments:null
                        .replace(R.id.fragment_cotainer, SettingsFragment.class, null)
                        // docs say to do this... so you can reorder them...
                        .setReorderingAllowed(true)
                        // commit it
                        .commit();
            }
            if (menuItem.getItemId() == R.id.groups_item) {
                getSupportFragmentManager().beginTransaction()
                        // where to add, class of fragment to add, optional arguments:null
                        .replace(R.id.fragment_cotainer, GroupFragment.class, null)
                        // docs say to do this... so you can reorder them...
                        .setReorderingAllowed(true)
                        // commit it
                        .commit();
            }
            if (menuItem.getItemId() == R.id.todos_items) {
                getSupportFragmentManager().beginTransaction()
                        // where to add, class of fragment to add, optional arguments:null
                        .replace(R.id.fragment_cotainer, TodoListFragment.class, null)
                        // docs say to do this... so you can reorder them...
                        .setReorderingAllowed(true)
                        // commit it
                        .commit();
            }
            drawer.close();
            return true;
        });

        // get access to user
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    // which fragment to display first
                    if (savedInstanceState == null) {

                        // important!
                        // fragment manager
                        getSupportFragmentManager().beginTransaction()
                                // where to add, class of fragment to add, optional arguments:null
                                .add(R.id.fragment_cotainer, HomeFragment.class, null)
                                // docs say to do this... so you can reorder them...
                                .setReorderingAllowed(true)
                                // commit it
                                .commit();
                    }
                }
            }
        });
    }
}