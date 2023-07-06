package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.sharif.timesync.entity.Meeting;
import edu.sharif.timesync.entity.MeetingChoice;
import edu.sharif.timesync.entity.MeetingState;

public class MeetingDatabaseManager {

    private static MeetingDatabaseManager meetingDatabaseManager;
    private static final String TABLE_NAME = "MeetingDB";
    private static final String MEETING_ID_FIELD = "meeting_id";
    private static final String MEETING_NAME_FIELD = "meeting_name";
    private static final String GROUP_NAME_FIELD = "group_name";
    private static final String IS_FINALIZED_FIELD = "is_finalized";

    private SQLDatabaseManager sqlDatabaseManager;

    public MeetingDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static MeetingDatabaseManager instanceOfMeetingDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (meetingDatabaseManager == null) {
            meetingDatabaseManager = new MeetingDatabaseManager(sqlDatabaseManager);
        }
        return meetingDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(MEETING_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(MEETING_NAME_FIELD)
                .append(" TEXT, ")
                .append(GROUP_NAME_FIELD)
                .append(" TEXT, ")
                .append(IS_FINALIZED_FIELD)
                .append(" INT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public boolean doesMeetingExists(String meetingName) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(MEETING_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{meetingName});
        return result.getCount() > 0;

    }

    public void testCreateNewMeeting(String meetingName) throws Exception {
        if (doesMeetingExists(meetingName)) {
            throw new Exception("meeting already exists");
        }

        ArrayList<String> usernames = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroupUsernames();
        if (usernames.size() <= 1) {
            throw new Exception("this group doesn't have any member except you!");
        }
    }

    public boolean createNewMeeting(String meetingName){
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        String groupName = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getName();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MEETING_NAME_FIELD, meetingName);
        contentValues.put(GROUP_NAME_FIELD, groupName);
        contentValues.put(IS_FINALIZED_FIELD, 0);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return true;
    }

    public ArrayList<Meeting> getAllMeetingsOfCurrentGroup() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();
        String currentGroupName = GroupUserMappingDatabaseManager.instanceOfGroupUserMappingDatabaseManager(sqlDatabaseManager)
                .getCurrentGroup().getName();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(GROUP_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{currentGroupName});

        MeetingChoiceDatabaseManager meetingChoiceDatabaseManager = MeetingChoiceDatabaseManager.instanceOfMeetingChoiceDatabaseManager(sqlDatabaseManager);
        UserDatabaseManager userDatabaseManager = UserDatabaseManager.instanceOfUserDatabaseManager(sqlDatabaseManager);


        ArrayList<Meeting> meetings = new ArrayList<>();
        while (result.moveToNext()) {
            String meetingName = result.getString(1);
            String groupName = result.getString(2);
            boolean isFinalized = result.getInt(3) == 1;
            ArrayList<MeetingChoice> choices = meetingChoiceDatabaseManager.getAcceptedMeetingChoice(meetingName);

            String loggedInUsername = userDatabaseManager.getLoggedInUser().getUsername();

            MeetingState meetingState;
            if (isFinalized) {
                meetingState = MeetingState.FINALIZED;
            } else {
                if (meetingChoiceDatabaseManager.hasUserVoted(meetingName, loggedInUsername)) {
                    meetingState = MeetingState.PENDING_VOTED;
                } else {
                    meetingState = MeetingState.PENDING_NOT_VOTED;
                }
            }

            meetings.add(new Meeting(meetingName, groupName, meetingState, choices));
        }
        return meetings;
    }

    public void finalizeMeetingIfFinalized(String meetingName) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        MeetingChoiceDatabaseManager meetingChoiceDatabaseManager = MeetingChoiceDatabaseManager.instanceOfMeetingChoiceDatabaseManager(sqlDatabaseManager);
        if (meetingChoiceDatabaseManager.isMeetingFinalized(meetingName)) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(IS_FINALIZED_FIELD, "1");

            sqLiteDatabase.update(TABLE_NAME, contentValues, MEETING_NAME_FIELD + " = ?", new String[]{meetingName});

        }
    }

    public boolean isFinalizedFromDatabase(String meetingName){
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(MEETING_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{meetingName});
        result.moveToFirst();

        return result.getInt(3) == 1;
    }
}
