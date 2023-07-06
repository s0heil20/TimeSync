package edu.sharif.timesync.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Meeting {
    private String name;
    private String groupName;

    private MeetingState meetingState;
    private ArrayList<MeetingChoice> acceptedChoice;

    public Meeting(String name, String groupName, MeetingState meetingState, ArrayList<MeetingChoice> acceptedChoice) {
        this.name = name;
        this.groupName = groupName;
        this.meetingState = meetingState;
        this.acceptedChoice = acceptedChoice;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }


    public MeetingState getMeetingState() {
        return meetingState;
    }

    public ArrayList<MeetingChoice> getAcceptedChoice() {
        return acceptedChoice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMeetingState(MeetingState meetingState) {
        this.meetingState = meetingState;
    }

    public void setAcceptedChoice(ArrayList<MeetingChoice> acceptedChoice) {
        this.acceptedChoice = acceptedChoice;
    }
}
