package com.danieldickeytodosharedproject1.api.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.danieldickeytodosharedproject1.api.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserViewModel extends ViewModel {
    FirebaseAuth auth;
    MutableLiveData<User> user = new MutableLiveData<>();
    MutableLiveData<RuntimeException> loginError = new MutableLiveData<>();

    // db for storing user data
    DatabaseReference db;

    public UserViewModel() {

        // init db
        db = FirebaseDatabase.getInstance().getReference();

        this.auth = FirebaseAuth.getInstance();
        this.auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = auth.getCurrentUser();
                loginError.setValue(null);
                if (fbUser == null) {
                    user.setValue(null);
                } else {
                    user.setValue(new User(fbUser));
                }
            }
        });
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password) /* ; */
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            loginError.setValue(new RuntimeException("Sign-up failed: " + e));
//                            e.printStackTrace();
                        }
                    }
                });
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password) /*; */
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            loginError.setValue(new RuntimeException("Login failed " + e));
//                            e.printStackTrace();
                        }
                    }
                });
    }

    public void signOut() {
        auth.signOut();
    }

    public void storeUserName(String name) {
        if (user.getValue() == null) {
            return;
        }
        db.child("UserData").child(user.getValue().getId()).child("name").setValue(name);
    }

    public void retrieveUserName() {
        db.child("UserData").child(user.getValue().getId()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    User u = user.getValue();
                    if (u != null) {
                        u.setName(String.valueOf(task.getResult().getValue()));
                        user.setValue(u);
                    }
                }
            }
        });
    }
}

