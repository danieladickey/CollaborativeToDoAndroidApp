package com.danieldickeytodosharedproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danieldickeytodosharedproject1.api.viewmodels.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    UserViewModel userViewModel; // login has a userViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // match to view
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);
        TextView signUp = findViewById(R.id.sign_up_link);

        // onclick listener
        loginButton.setOnClickListener((view) -> {
            userViewModel.login(email.getText().toString(), password.getText().toString());
        });

        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

    }

    // if user is logged in send them to home screen
    @Override
    protected void onStart() {
        super.onStart();
        userViewModel.getUser().observe(this, (user) -> {
            if (user != null) {
                Log.d("TAG", "onStart: " + user + ", " + user.getId() + ", " + user.getEmail());
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}