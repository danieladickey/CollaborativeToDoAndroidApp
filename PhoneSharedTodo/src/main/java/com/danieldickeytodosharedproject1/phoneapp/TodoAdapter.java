package com.danieldickeytodosharedproject1.phoneapp;

import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.danieldickeytodosharedproject1.api.CustomAdapter;
import com.danieldickeytodosharedproject1.api.models.Todo;

public class TodoAdapter extends CustomAdapter<Todo> {
    ItemClickedListener listener;

    public TodoAdapter(ObservableArrayList<Todo> data, ItemClickedListener listener) {
        super(data);
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.todo_list_item;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = data.get(position);
        CheckBox textView = holder.itemView.findViewById(R.id.checkBoxDisplay);
        textView.setText(todo.task);
        textView.setChecked(todo.complete);

        textView.setOnClickListener(view -> {
            System.out.println(todo.id + " " + todo.task + " " + todo.complete);
            listener.onClick(todo);
        });
    }
}
