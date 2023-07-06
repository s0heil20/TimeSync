package edu.sharif.timesync.groupDetailedMenu.jobs;

import java.util.List;

public class JobListItem {
    String name;
    List<String> assignedUsers;
    public JobListItem(String name, List<String> assignedUsers) {
        this.name = name;
        this.assignedUsers = assignedUsers;
    }

    public String getName() {
        return name;
    }

    public List<String> getAssignedUsers() {
        return assignedUsers;
    }
}
