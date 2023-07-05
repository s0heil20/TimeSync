package edu.sharif.timesync.database;

import java.util.ArrayList;

import edu.sharif.timesync.entity.Group;

public class GroupDatabaseManager {
    private static GroupDatabaseManager groupDatabaseManager;
    private static final String TABLE_NAME = "GroupDB";
    private static final String GROUP_ID_FIELD = "group_id";
    private static final String NAME_FIELD = "name";
    private static final String USERS_FIELD = "users";
    private static final String JOBS_FIELD = "jobs";

    private SQLDatabaseManager sqlDatabaseManager;

    public GroupDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static GroupDatabaseManager instanceOfGroupDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (groupDatabaseManager == null) {
            groupDatabaseManager = new GroupDatabaseManager(sqlDatabaseManager);
        }
        return groupDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(GROUP_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(USERS_FIELD)
                .append(" TEXT, ")
                .append(JOBS_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public ArrayList<Group> getGroupsByUsername(String username){
        return null;
    }

    public ArrayList<Group> getGroupsOfLoggedInUser(){
        return null;
    }

    public void addGroupByName(String name){
        return;
    }
}
