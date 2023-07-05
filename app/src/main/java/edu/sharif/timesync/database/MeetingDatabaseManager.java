package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.sharif.timesync.entity.User;

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

    public boolean createNewMeeting(String meetingName){
        if (doesMeetingExists(meetingName)){
            return false;
        }
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        String groupName = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getName();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MEETING_ID_FIELD, meetingName);
        contentValues.put(GROUP_NAME_FIELD, groupName);
        contentValues.put(IS_FINALIZED_FIELD, 0);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return true;
    }
}
