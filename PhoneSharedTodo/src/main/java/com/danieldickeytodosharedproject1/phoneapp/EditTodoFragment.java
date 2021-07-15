package com.danieldickeytodosharedproject1.phoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.danieldickeytodosharedproject1.api.models.Todo;
import com.danieldickeytodosharedproject1.api.viewmodels.TodoViewModel;
import com.danieldickeytodosharedproject1.api.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class EditTodoFragment extends Fragment {
    UserViewModel userViewModel;
    TodoViewModel todoViewModel;
    private boolean isTalking = false;
    String thisID;
    String thisTask;
    Boolean thisComplete;

    public EditTodoFragment() {
        super(R.layout.fragment_edit_todo);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        super.onViewCreated(view, savedInstanceState);

        todoViewModel = new ViewModelProvider(getActivity()).get(TodoViewModel.class);

        EditText taskEditText = view.findViewById(R.id.edit_task);
        CheckBox checkBox = view.findViewById(R.id.task_is_complete);

        Bundle myBundle = this.getArguments();
        if (myBundle != null) {
            thisID = myBundle.getString("id");
            thisTask = myBundle.getString("task");
            thisComplete = myBundle.getBoolean("complete");
            taskEditText.setText(thisTask);
            checkBox.setChecked(thisComplete);
            System.out.println("after: " + thisComplete);

        }


        // modify current to-do
        view.findViewById(R.id.save_task_button).setOnClickListener((v) -> {
            // modify current task
            if (thisID == null) {
                Todo current = todoViewModel.getTodoItems().get(todoViewModel.getTodoItems().size() - 1);
                todoViewModel.editTodoItem(current.id, taskEditText.getText().toString(), checkBox.isChecked());
            }
            else {

                todoViewModel.editTodoItem(thisID, taskEditText.getText().toString(), checkBox.isChecked());
            }


            // go back to list of to-dos
            getActivity().getSupportFragmentManager().popBackStack();
        });

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
                taskEditText.setText(text);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        view.findViewById(R.id.mic).setOnClickListener(v -> {
            if (!isTalking) {
                recognizer.startListening(recognizerIntent);
                isTalking = true;
            }
            else {
                isTalking = false;
                recognizer.stopListening();
            }
        });

        // delete current task
        view.findViewById(R.id.delete_task_button).setOnClickListener(v -> {
            // TODO: delete todo, which one is the current one?
            todoViewModel.deleteTodoItem(thisID);

            // go back to list of to-dos
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((FloatingActionButton)getActivity().findViewById(R.id.add_new_task_fab)).hide();
    }
}