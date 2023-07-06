package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.sharif.timesync.entity.Job;

public class GroupJobMappingDatabaseManager implements EntityDatabaseManager {
    private static GroupJobMappingDatabaseManager groupJobMappingDatabaseManager;
    private static final String TABLE_NAME = "GroupJobMappingDB";
    private static final String GROUP_ID_FIELD = "group_id";
    private static final String GROUP_NAME_FIELD = "name";
    private static final String JOB_NAME_FIELD = "job";

    private SQLDatabaseManager sqlDatabaseManager;

    public GroupJobMappingDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static GroupJobMappingDatabaseManager instanceOfGroupJobMappingDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (groupJobMappingDatabaseManager == null) {
            groupJobMappingDatabaseManager = new GroupJobMappingDatabaseManager(sqlDatabaseManager);
        }
        return groupJobMappingDatabaseManager;
    }

    @Override
    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(GROUP_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(GROUP_NAME_FIELD)
                .append(" TEXT NOT NULL, ")
                .append(JOB_NAME_FIELD)
                .append(" TEXT NOT NULL)");

        return sql.toString();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public ArrayList<String> getJobsOfCurrentGroup() {
        String groupName = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getName();

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(GROUP_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{groupName});

        ArrayList<String> jobs = new ArrayList<>();
        while (result.moveToNext()) {
            jobs.add(result.getString(2));
        }
        return jobs;
    }


    public void addJobByName(String jobName) {
        String groupName = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getName();

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_NAME_FIELD, groupName);
        contentValues.put(JOB_NAME_FIELD, jobName);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public boolean doesJobExistInCurrentGroup(String jobName) {
        String groupName = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getName();

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(GROUP_NAME_FIELD)
                .append(" = ? AND ")
                .append(JOB_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{groupName, jobName});
        return result.getCount() > 0;
    }

}
