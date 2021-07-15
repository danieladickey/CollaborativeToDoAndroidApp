package com.danieldickeytodosharedproject1.api.viewmodels;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.danieldickeytodosharedproject1.api.models.Todo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TodoViewModel extends ViewModel {
    private ObservableArrayList<Todo> todoItems; // array list of TodoItem
    private final DatabaseReference db;  // reference to firebase

    // constructor
    public TodoViewModel() {
        // init db
        db = FirebaseDatabase.getInstance().getReference();
    }

    // getter
    public ObservableArrayList<Todo> getTodoItems() {
        if (todoItems == null) {
            todoItems = new ObservableArrayList<>();
            loadTodoItems();
        }
        return todoItems;
    }

    private void loadTodoItems() {
        // firebase stuff
        // if anything in the todoItems "folder" on firebase changes
        db.child("/todoItems").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Todo todo = snapshot.getValue(Todo.class);
                todo.id = snapshot.getKey();
                todoItems.add(todo);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Todo todo = snapshot.getValue(Todo.class);
                todo.id = snapshot.getKey();
                int index = todoItems.indexOf(todo);
                todoItems.set(index, todo);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Todo todo = snapshot.getValue(Todo.class);
                todo.id = snapshot.getKey();
                todoItems.remove(todo);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addTodoItem(String task, Boolean complete) {
        // create a new todoItem to add
        Todo newTodoItem = new Todo(task, complete);

        // use firebase to set the value
        // push randomly generates a key
        db.child("/todoItems").push().setValue(newTodoItem);
    }

    public void editTodoItem(String id, String task, Boolean complete) {
        db.child("/todoItems").child(id).child("complete").setValue(complete);
        db.child("/todoItems").child(id).child("task").setValue(task);
    }

    public void updateTodoItemStatus(Todo todoItem){
        //Then your update method only needs to update the db
        //Set compete to new boolean value (whatever it wasn't before)
        db.child("/todoItems").child(todoItem.id).child("complete").setValue(!todoItem.complete);
    }

    public void deleteTodoItem(String id) {
        db.child("/todoItems").child(id).removeValue();
    }
}
