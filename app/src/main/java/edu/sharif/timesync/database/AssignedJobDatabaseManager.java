package edu.sharif.timesync.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.sharif.timesync.entity.Job;

public class AssignedJobDatabaseManager {
    private static AssignedJobDatabaseManager jobDatabaseManager;
    private static final String TABLE_NAME = "JobDB";
    private static final String JOB_ID_FIELD = "job_id";
    private static final String JOB_NAME_FIELD = "job";
    private static final String USERNAME_FIELD = "user";

    private SQLDatabaseManager sqlDatabaseManager;

    public AssignedJobDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static AssignedJobDatabaseManager instanceOfJobDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (jobDatabaseManager == null) {
            jobDatabaseManager = new AssignedJobDatabaseManager(sqlDatabaseManager);
        }
        return jobDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(JOB_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(JOB_NAME_FIELD)
                .append(" TEXT, ")
                .append(USERNAME_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public void assignUsersToJobByName(ArrayList<String> usernames, String jobName){

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        for (String username : usernames) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(JOB_NAME_FIELD, jobName);
            contentValues.put(USERNAME_FIELD, username);

            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }
    }

    public ArrayList<String> getCurrentUsersJobs() {
        String username = sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername();

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USERNAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{username});

        ArrayList<String> jobs = new ArrayList<>();
        while (result.moveToNext()) {
            jobs.add(result.getString(1));
        }
        return jobs;
    }

    public ArrayList<String> getJobUsers(String jobName) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(JOB_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{jobName});

        ArrayList<String> users = new ArrayList<>();
        while (result.moveToNext()) {
            users.add(result.getString(2));
        }
        return users;
    }

}
