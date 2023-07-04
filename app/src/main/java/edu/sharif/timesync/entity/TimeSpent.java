package edu.sharif.timesync.entity;

import java.util.Date;


public class TimeSpent {
    private User user;
    private Job job;
    private Date timeLength;

    public TimeSpent(User user, Job job, Date timeLength) {
        this.user = user;
        this.job = job;
        this.timeLength = timeLength;
    }

    public User getUser() {
        return user;
    }

    public Job getJob() {
        return job;
    }

    public Date getTimeLength() {
        return timeLength;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setTimeLength(Date timeLength) {
        this.timeLength = timeLength;
    }
}
