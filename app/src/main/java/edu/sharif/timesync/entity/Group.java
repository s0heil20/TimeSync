package edu.sharif.timesync.entity;

import java.util.ArrayList;

public class Group {

    private String name;
    private User adminUser;
    private ArrayList<User> users;
    private ArrayList<Job> jobs;

    public Group(String name, User adminUser) {
        this.name = name;
        this.adminUser = adminUser;
        this.jobs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
}
