package edu.sharif.timesync.groupDetailedMenu.meeting;

import edu.sharif.timesync.entity.Meeting;

public class MeetingListItem {
    private Meeting meeting;

    public MeetingListItem(Meeting meeting) {
        this.meeting = meeting;
    }

    public Meeting getMeeting() {
        return meeting;
    }
}
