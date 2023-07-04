package edu.sharif.timesync.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Meeting {
    private int meetingId;
    private Group group;
    private ArrayList<Date> candidateTimes;

    private HashMap<String, ArrayList<Date>> availableTimesForUsers;

    public Meeting(Group group, int meetingId, ArrayList<Date> candidateTimes) {
        this.group = group;
        this.meetingId = meetingId;
        this.candidateTimes = candidateTimes;
        this.availableTimesForUsers = new HashMap<>();
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

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public void setCandidateTimes(ArrayList<Date> candidateTimes) {
        this.candidateTimes = candidateTimes;
    }
}
