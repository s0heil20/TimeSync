package edu.sharif.timesync.groupListMenu;

public class GroupListItem {
    String name;
    int groupId;


    public GroupListItem(String name, int groupId) {
        this.name = name;
        this.groupId = groupId;

    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }


}
