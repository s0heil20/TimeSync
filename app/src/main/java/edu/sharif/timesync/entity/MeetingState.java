package edu.sharif.timesync.entity;

public enum MeetingState {
    FINALIZED(1),
    PENDING_NOT_VOTED(2),
    PENDING_VOTED(3);

    private int meetingStateIndex;


    private MeetingState(int meetingStateIndex) {
        this.meetingStateIndex = meetingStateIndex;
    }


    public static MeetingState getMeetingState(int meetingIndex) {
        for (MeetingState value : MeetingState.values()) {
            if (value.meetingStateIndex == meetingIndex) {
                return value;
            }
        }
        return PENDING_NOT_VOTED;
    }

}
