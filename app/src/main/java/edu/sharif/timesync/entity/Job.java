package edu.sharif.timesync.entity;

import java.util.ArrayList;

public class Job {
    private String name;
    private ArrayList<User> assignedUsers;

    public Job(String name) {
        this.name = name;
        this.assignedUsers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignedUsers(ArrayList<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
