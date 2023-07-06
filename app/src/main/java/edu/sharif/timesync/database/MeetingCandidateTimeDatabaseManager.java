package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;

import edu.sharif.timesync.entity.Meeting;
import edu.sharif.timesync.entity.MeetingChoice;

public class MeetingCandidateTimeDatabaseManager {

    private static MeetingCandidateTimeDatabaseManager meetingCandidateTimeDatabaseManager;

    private static final String TABLE_NAME = "MeetingCandidateDB";
    private static final String ID_FIELD = "id";
    private static final String MEETING_NAME_FIELD = "meeting_name";
    private static final String CHOICE_VALUE_FIELD = "choice_value";

    private SQLDatabaseManager sqlDatabaseManager;

    public MeetingCandidateTimeDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static MeetingCandidateTimeDatabaseManager instanceOfMeetingCandidateDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (meetingCandidateTimeDatabaseManager == null) {
            meetingCandidateTimeDatabaseManager = new MeetingCandidateTimeDatabaseManager(sqlDatabaseManager);
        }
        return meetingCandidateTimeDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(MEETING_NAME_FIELD)
                .append(" TEXT, ")
                .append(CHOICE_VALUE_FIELD)
                .append(" INT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public void addCandidateTime(String meetingName, MeetingChoice meetingChoice) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MEETING_NAME_FIELD, meetingName);
        contentValues.put(CHOICE_VALUE_FIELD, meetingChoice.convertToInt());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void removeCandidateTime(String meetingName, MeetingChoice meetingChoice) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, CHOICE_VALUE_FIELD + "=?", new String[]{meetingChoice.convertToInt() + ""});
        sqLiteDatabase.close();
    }

    public void addAllCandidateTime(String meetingName, ArrayList<MeetingChoice> meetingChoices){
        for (MeetingChoice meetingChoice : meetingChoices) {
            addCandidateTime(meetingName, meetingChoice);
        }
    }

    public ArrayList<MeetingChoice> getMeetingCandidateTimes(String meetingName) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(MEETING_NAME_FIELD)
                .append(" = ?");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{meetingName});

        ArrayList<MeetingChoice> choices = new ArrayList<>();
        while (result.moveToNext()) {
            choices.add(MeetingChoice.getMeetingChoiceFromInt(result.getInt(2)));
        }
        return choices;
    }
}
