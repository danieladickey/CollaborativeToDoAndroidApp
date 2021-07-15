package com.danieldickeytodosharedproject1.api.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Group {
    // Attributes
    public String name; // Name of a group
    public ArrayList<User> users;
    public String password;
    @Exclude // don't put this into firebase because we will set it manually after retrieving it from firebase
    public String id; // the randomly generated key in firebase

    // Default constructor for firebase to use
    public Group() {
    }

    // Overloaded Constructor
    public Group(String name, ArrayList<User> users, String password) {
        this.password = password;
        this.name = name;
        this.users = users;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
