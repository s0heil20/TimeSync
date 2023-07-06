package edu.sharif.timesync.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MeetingChoice {
    public static List<String> days =
            Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
    private int timeIndex;
    private String day;

    public MeetingChoice(int timeIndex, String day) {
        this.timeIndex = timeIndex;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingChoice that = (MeetingChoice) o;
        return timeIndex == that.timeIndex && Objects.equals(day, that.day);
    }

    public int convertToInt() {
        return (timeIndex - 1) * 7 + days.indexOf(day);
    }

    public static MeetingChoice getMeetingChoiceFromInt(int number){
        String day = days.get(number%7);
        int index = number/7 + 1;
        return new MeetingChoice(index, day);
    }

}
