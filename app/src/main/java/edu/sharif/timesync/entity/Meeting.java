package edu.sharif.timesync.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Meeting {
    private int meetingId;
    private String name;
    private Group group;
    private ArrayList<Date> candidateTimes;

    private HashMap<String, ArrayList<Date>> availableTimesForUsers;

    public Meeting(int meetingId, String name, Group group, ArrayList<Date> candidateTimes, HashMap<String, ArrayList<Date>> availableTimesForUsers) {
        this.meetingId = meetingId;
        this.name = name;
        this.group = group;
        this.candidateTimes = candidateTimes;
        this.availableTimesForUsers = availableTimesForUsers;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public ArrayList<Date> getCandidateTimes() {
        return candidateTimes;
    }


    public void setGroup(Group group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public void setCandidateTimes(ArrayList<Date> candidateTimes) {
        this.candidateTimes = candidateTimes;
    }
}
