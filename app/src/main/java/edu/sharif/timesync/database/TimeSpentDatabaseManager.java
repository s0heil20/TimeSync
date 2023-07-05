package edu.sharif.timesync.database;


import java.util.ArrayList;
import java.util.HashMap;

import edu.sharif.timesync.entity.Job;
import edu.sharif.timesync.entity.TimeSpent;
import edu.sharif.timesync.entity.User;

public class TimeSpentDatabaseManager {
    private static TimeSpentDatabaseManager timeSpentDatabaseManager;
    private static final String TABLE_NAME = "TimeSpentDB";
    private static final String ID_FIELD = "id";
    private static final String USER_FIELD = "user";
    private static final String JOB_FIELD = "job";
    private static final String TIME_LENGTH_FIELD = "time_length";

    private SQLDatabaseManager sqlDatabaseManager;


    public TimeSpentDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static TimeSpentDatabaseManager instanceOfGroupDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (timeSpentDatabaseManager == null) {
            timeSpentDatabaseManager = new TimeSpentDatabaseManager(sqlDatabaseManager);
        }
        return timeSpentDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USER_FIELD)
                .append(" TEXT, ")
                .append(JOB_FIELD)
                .append(" TEXT, ")
                .append(TIME_LENGTH_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public void assignTimeSpentToJob(String jobId, String username, int minute, int hours){
        return;
    }

    public ArrayList<TimeSpent> getAllTimeSpentOfWeekOfUser(String username) {
        return null;
    }

    public TimeSpent getTimeSpentOnJobByUser(String jobId, String username){
        return null;
    }

    public HashMap<Job, TimeSpent> getTimeSpentOnEachJobByUser(ArrayList<String> jobIds, String username){
        return null;
    }

    public ArrayList<TimeSpent> getTimeSpentByUserWeekly(User user) {
        return null;
    }

    public ArrayList<TimeSpent> getAverageTimeSpentOnEachJobByCurrentGroup() {
        return null;
    }

    public ArrayList<TimeSpent> getAverageTimeSpentByCurrentGroupWeekly() {
        return null;
    }
}
