package com.danieldickeytodosharedproject1.phoneapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.danieldickeytodosharedproject1.api.viewmodels.UserViewModel;

public abstract class ActivityWithUser extends AppCompatActivity{
    protected UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userViewModel.getUser().observe(this, (user) -> {
            if (user == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
