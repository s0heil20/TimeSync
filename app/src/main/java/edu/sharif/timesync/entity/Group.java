package edu.sharif.timesync.entity;

import java.util.ArrayList;

public class Group {

    private String name;
    private String adminUsername;

    public Group(String name, String adminUsername) {
        this.name = name;
        this.adminUsername = adminUsername;
    }

    public String getName() {
        return name;
    }


    public String getAdminUsername() {
        return adminUsername;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
}
