package com.danieldickeytodosharedproject1.api.models;

import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Todo {
    public String task;
    public Boolean complete;
    @Exclude
    public String id;

    public Todo() {
    }

    public Todo(String task, Boolean complete) {
        this.task = task;
        this.complete = complete;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Todo) {
            Todo todo = (Todo) obj;
            return todo.id.equals(this.id);
        }
        return false;
    }
}
