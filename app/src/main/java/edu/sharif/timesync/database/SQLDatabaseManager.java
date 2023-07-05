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
    private final GroupDatabaseManager groupDatabaseManager;

    private final JobDatabaseManager jobDatabaseManager;
    private final MeetingDatabaseManager meetingDatabaseManager;
    private final TimeSpentDatabaseManager timeSpentDatabaseManager;


    public SQLDatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        userDatabaseManager = UserDatabaseManager.instanceOfUserDatabaseManager(this);
        groupDatabaseManager = GroupDatabaseManager.instanceOfGroupDatabaseManager(this);
        jobDatabaseManager = JobDatabaseManager.instanceOfJobDatabaseManager(this);
        meetingDatabaseManager = MeetingDatabaseManager.instanceOfMeetingDatabaseManager(this);
        timeSpentDatabaseManager = TimeSpentDatabaseManager.instanceOfGroupDatabaseManager(this);
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

    public GroupDatabaseManager getGroupDatabaseManager() {
        return groupDatabaseManager;
    }

    public JobDatabaseManager getJobDatabaseManager() {
        return jobDatabaseManager;
    }

    public MeetingDatabaseManager getMeetingDatabaseManager() {
        return meetingDatabaseManager;
    }

    public TimeSpentDatabaseManager getTimeSpentDatabaseManager() {
        return timeSpentDatabaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(userDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(groupDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(jobDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(meetingDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(timeSpentDatabaseManager.createTableString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + userDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + groupDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + jobDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + meetingDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + timeSpentDatabaseManager.getTableName());
        onCreate(sqLiteDatabase);
    }

    public void dropTables() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        onUpgrade(sqLiteDatabase, 1, 2);
    }

}