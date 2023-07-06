package edu.sharif.timesync.groupDetailedMenu.meeting;

import edu.sharif.timesync.entity.Meeting;
import edu.sharif.timesync.entity.MeetingState;

public class MeetingListItem {
    private String name;
    private MeetingState meetingState;

    public MeetingListItem(String name, MeetingState meetingState) {
        this.name = name;
        this.meetingState = meetingState;
    }

    public String getName() {
        return name;
    }

    public MeetingState getMeetingState() {
        return meetingState;
    }
}
