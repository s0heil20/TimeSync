package edu.sharif.timesync.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import edu.sharif.timesync.entity.Job;
import edu.sharif.timesync.entity.TimeSpent;
import edu.sharif.timesync.entity.User;

public class TimeSpentDatabaseManager {
    private static TimeSpentDatabaseManager timeSpentDatabaseManager;
    private static final String TABLE_NAME = "TimeSpentDB";
    private static final String ID_FIELD = "id";
    private static final String USERNAME_FIELD = "username";
    private static final String JOB_NAME_FIELD = "job_name";
    private static final String TIME_LENGTH_FIELD = "time_length";
    private static final String TIME_DAY_FIELD = "time_day";

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
                .append(USERNAME_FIELD)
                .append(" TEXT, ")
                .append(JOB_NAME_FIELD)
                .append(" TEXT, ")
                .append(TIME_DAY_FIELD)
                .append(" TEXT, ")
                .append(TIME_LENGTH_FIELD)
                .append(" INT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public void assignTimeSpentToJob(String jobName, String username, int minute, String day) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_FIELD, username);
        contentValues.put(JOB_NAME_FIELD, jobName);
        contentValues.put(TIME_LENGTH_FIELD, minute);
        contentValues.put(TIME_DAY_FIELD, day);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    private HashMap<String, Integer> getTimeSpentByUser(String username, String accordingToField) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();
        GroupJobMappingDatabaseManager groupJobMappingDatabaseManager = GroupJobMappingDatabaseManager.instanceOfGroupJobMappingDatabaseManager(sqlDatabaseManager);


        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT ")
                .append(JOB_NAME_FIELD)
                .append(", ")
                .append(accordingToField)
                .append(", SUM(")
                .append(TIME_LENGTH_FIELD)
                .append(") FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USERNAME_FIELD)
                .append(" = ?")
                .append(" GROUP BY ")
                .append(accordingToField);

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{username});

        HashMap<String, Integer> times = new HashMap<>();
        while (result.moveToNext()) {
            String jobName = result.getString(0);
            if (groupJobMappingDatabaseManager.doesJobExistInCurrentGroup(jobName)) {
                times.put(result.getString(1), result.getInt(2));
            }
        }
        return times;
    }

    public HashMap<String, Integer> getTimeSpentByUserWeekly(String username) {
        return getTimeSpentByUser(username, TIME_DAY_FIELD);

    }

    public HashMap<String, Integer> getTimeSpentByUserByJobName(String username) {
        return getTimeSpentByUser(username, JOB_NAME_FIELD);
    }

    private HashMap<String, Integer> getAverageTimeSpent(String accordingToField) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();
        GroupUserMappingDatabaseManager groupUserMappingDatabaseManager = GroupUserMappingDatabaseManager.instanceOfGroupUserMappingDatabaseManager(sqlDatabaseManager);
        GroupJobMappingDatabaseManager groupJobMappingDatabaseManager = GroupJobMappingDatabaseManager.instanceOfGroupJobMappingDatabaseManager(sqlDatabaseManager);


        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT ")
                .append(JOB_NAME_FIELD)
                .append(", ")
                .append(accordingToField)
                .append(", SUM(")
                .append(TIME_LENGTH_FIELD)
                .append(") FROM ")
                .append(TABLE_NAME)
                .append(" GROUP BY ")
                .append(accordingToField);

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{});


        int numberOfUsers = groupUserMappingDatabaseManager.getCurrentGroupUsernames().size() - 1;
        HashMap<String, Integer> times = new HashMap<>();
        while (result.moveToNext()) {
            String jobName = result.getString(0);
            if (groupJobMappingDatabaseManager.doesJobExistInCurrentGroup(jobName)) {
                times.put(result.getString(1),result.getInt(2) / numberOfUsers);
            }
        }
        return times;
    }

    public HashMap<String, Integer> getAverageTimeSpentOnEachJobByCurrentGroup() {
        return getAverageTimeSpent(JOB_NAME_FIELD);
    }

    public HashMap<String, Integer> getAverageTimeSpentByCurrentGroupWeekly() {
        return getAverageTimeSpent(TIME_DAY_FIELD);
    }
}
