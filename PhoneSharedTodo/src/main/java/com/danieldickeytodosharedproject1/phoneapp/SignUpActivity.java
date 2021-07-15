package com.danieldickeytodosharedproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.danieldickeytodosharedproject1.api.viewmodels.UserViewModel;

public class SignUpActivity extends AppCompatActivity {
    UserViewModel userViewModel; // login has a userViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // match to view
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        EditText email = findViewById(R.id.signup_email);
        EditText emailConfirmation = findViewById(R.id.signup_email_confirmation);
        EditText password = findViewById(R.id.signup_password);
        EditText passwordConfirmation = findViewById(R.id.signup_password_confirmtion);
//        EditText name = findViewById(R.id.signup_name);
        Button signUpButton = findViewById(R.id.signup_button);

        //onclick listener
        signUpButton.setOnClickListener((view) -> {
            if (email.getText().toString().equals(emailConfirmation.getText().toString()) &&
                    password.getText().toString().equals(passwordConfirmation.getText().toString())) {
                userViewModel.signUp(email.getText().toString(), password.getText().toString());
//                userViewModel.storeUserName(name.getText().toString());
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.d("WTF", "emails or passwords don't match");
            }
        });
    }
}