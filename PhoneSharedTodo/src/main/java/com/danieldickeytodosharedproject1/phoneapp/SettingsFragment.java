package com.danieldickeytodosharedproject1.phoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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

import java.util.ArrayList;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    UserViewModel userViewModel;


    private boolean isTalking = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();

        // elements on screen
        TextView currentScreenName = view.findViewById(R.id.curren_screen_name);
        EditText setScreenName = view.findViewById(R.id.set_screen_name);
        Button saveNameButton = view.findViewById(R.id.home_save_name_button);
        FloatingActionButton mic = view.findViewById(R.id.talk);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        super.onViewCreated(view, savedInstanceState);

        // speech input
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());

        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                System.out.println("Error: " + i);
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String text = results.get(0);
                setScreenName.setText(text);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        view.findViewById(R.id.talk).setOnClickListener(v -> {
            if (!isTalking) {
                        recognizer.startListening(recognizerIntent);
                        isTalking = true;
            }
            else {
                isTalking = false;
                recognizer.stopListening();
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    userViewModel.retrieveUserName();

                    // display current screen name
                    if (user.getName() == null) {
                        currentScreenName.setText("Current Screen Name: ");
                    }
                    else {
                        currentScreenName.setText("Current Screen Name: " + user.getName());
                    }
                }
            }
        });

        // save new screen name
        saveNameButton.setOnClickListener(v -> {
            userViewModel.storeUserName(setScreenName.getText().toString());
//            userViewModel.retrieveUserName();
//            currentScreenName.setText("Current Screen Name: " + userViewModel.getUser().getValue().getName());
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();
    }
}