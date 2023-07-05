package edu.sharif.timesync.entity;

import java.util.ArrayList;

public class Job {
    private int jobId;
    private String name;
    private ArrayList<User> assignedUsers;

    public Job(int jobId, String name, ArrayList<User> assignedUsers) {
        this.jobId = jobId;
        this.name = name;
        this.assignedUsers = assignedUsers;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getAssignedUsers() {
        return assignedUsers;
    }

    public int getJobId() {
        return jobId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignedUsers(ArrayList<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
