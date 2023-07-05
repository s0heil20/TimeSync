package edu.sharif.timesync.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLDatabaseManager extends SQLiteOpenHelper {

    private static SQLDatabaseManager sqlDatabaseManager;
    private static final String DATABASE_NAME = "AppDB";
    private static final int DATABASE_VERSION = 1;

    private final UserDatabaseManager userDatabaseManager;
    private final GroupUserMappingDatabaseManager groupUserMappingDatabaseManager;

    private final AssignedJobDatabaseManager assignedJobDatabaseManager;
    private final MeetingDatabaseManager meetingDatabaseManager;

    private final MeetingChoiceDatabaseManager meetingChoiceDatabaseManager;
    private final MeetingCandidateTimeDatabaseManager meetingCandidateTimeDatabaseManager;
    private final TimeSpentDatabaseManager timeSpentDatabaseManager;
    private final GroupJobMappingDatabaseManager groupJobMappingDatabaseManager;


    public SQLDatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        userDatabaseManager = UserDatabaseManager.instanceOfUserDatabaseManager(this);
        groupUserMappingDatabaseManager = GroupUserMappingDatabaseManager.instanceOfGroupUserMappingDatabaseManager(this);
        assignedJobDatabaseManager = AssignedJobDatabaseManager.instanceOfJobDatabaseManager(this);
        meetingDatabaseManager = MeetingDatabaseManager.instanceOfMeetingDatabaseManager(this);
        meetingChoiceDatabaseManager = MeetingChoiceDatabaseManager.instanceOfMeetingChoiceDatabaseManager(this);
        meetingCandidateTimeDatabaseManager = MeetingCandidateTimeDatabaseManager.instanceOfMeetingCandidateDatabaseManager(this);
        timeSpentDatabaseManager = TimeSpentDatabaseManager.instanceOfGroupDatabaseManager(this);
        groupJobMappingDatabaseManager = GroupJobMappingDatabaseManager.instanceOfGroupJobMappingDatabaseManager(this);
    }

    public static SQLDatabaseManager instanceOfDatabase(Context context) {
        if (sqlDatabaseManager == null) {
            sqlDatabaseManager = new SQLDatabaseManager(context);
        }
        return sqlDatabaseManager;
    }

    public UserDatabaseManager getUserDatabaseManager() {
        return userDatabaseManager;
    }

    public GroupUserMappingDatabaseManager getGroupUserMappingDatabaseManager() {
        return groupUserMappingDatabaseManager;
    }

    public AssignedJobDatabaseManager getJobDatabaseManager() {
        return assignedJobDatabaseManager;
    }

    public MeetingDatabaseManager getMeetingDatabaseManager() {
        return meetingDatabaseManager;
    }

    public MeetingChoiceDatabaseManager getMeetingChoiceDatabaseManager() {
        return meetingChoiceDatabaseManager;
    }

    public MeetingCandidateTimeDatabaseManager getMeetingCandidateTimeDatabaseManager() {
        return meetingCandidateTimeDatabaseManager;
    }

    public TimeSpentDatabaseManager getTimeSpentDatabaseManager() {
        return timeSpentDatabaseManager;
    }

    public GroupJobMappingDatabaseManager getGroupJobMappingDatabaseManager() {
        return groupJobMappingDatabaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(userDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(groupUserMappingDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(assignedJobDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(meetingDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(meetingChoiceDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(meetingCandidateTimeDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(timeSpentDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(groupJobMappingDatabaseManager.createTableString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + userDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + groupUserMappingDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + assignedJobDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + meetingDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + meetingChoiceDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + meetingCandidateTimeDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + timeSpentDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + groupJobMappingDatabaseManager.getTableName());
        onCreate(sqLiteDatabase);
    }

    public void dropTables() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        onUpgrade(sqLiteDatabase, 1, 2);
    }

}