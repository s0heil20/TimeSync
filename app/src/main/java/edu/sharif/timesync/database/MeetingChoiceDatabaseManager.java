package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.sharif.timesync.entity.Meeting;
import edu.sharif.timesync.entity.MeetingChoice;

public class MeetingChoiceDatabaseManager {

    private static MeetingChoiceDatabaseManager meetingChoiceDatabaseManager;

    private static final String TABLE_NAME = "MeetingChoiceDB";
    private static final String ID_FIELD = "id";
    private static final String MEETING_NAME_FIELD = "meeting_name";
    private static final String USERNAME_FIELD = "username";
    private static final String CHOICE_VALUE_FIELD = "choice_value";

    private SQLDatabaseManager sqlDatabaseManager;

    public MeetingChoiceDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static MeetingChoiceDatabaseManager instanceOfMeetingChoiceDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (meetingChoiceDatabaseManager == null) {
            meetingChoiceDatabaseManager = new MeetingChoiceDatabaseManager(sqlDatabaseManager);
        }
        return meetingChoiceDatabaseManager;
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
                .append(USERNAME_FIELD)
                .append(" TEXT, ")
                .append(CHOICE_VALUE_FIELD)
                .append(" INT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public void addChoiceTime(String meetingName, String username, MeetingChoice meetingChoice) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MEETING_NAME_FIELD, meetingName);
        contentValues.put(USERNAME_FIELD, username);
        contentValues.put(CHOICE_VALUE_FIELD, meetingChoice.convertToInt());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    private HashSet<Integer> getUsersChoicesAsInt(String meetingName, String username) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(MEETING_NAME_FIELD)
                .append(" = ? AND ")
                .append(USERNAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{meetingName, username});

        HashSet<Integer> choicesInt = new HashSet<>();
        while (result.moveToNext()) {
            choicesInt.add(result.getInt(3));
        }
        return choicesInt;
    }

    public ArrayList<MeetingChoice> getAcceptedMeetingChoice(String meetingName){
        ArrayList<String> usernames = GroupUserMappingDatabaseManager.
                instanceOfGroupUserMappingDatabaseManager(sqlDatabaseManager).getCurrentGroupUsernames();

        List<Integer> initialList = new ArrayList<>();

        for (int i = 0; i < 49; i++) {
            initialList.add(i);
        }

        HashSet<Integer> acceptedTimes = new HashSet<>(initialList);
        HashSet<Integer> newUserTimes;
        for (String username : usernames) {
            newUserTimes = getUsersChoicesAsInt(meetingName, username);
            acceptedTimes.retainAll(newUserTimes);
        }

        ArrayList<MeetingChoice> meetingChoices = new ArrayList<>();
        for (Integer acceptedTime : acceptedTimes) {
            meetingChoices.add(MeetingChoice.getMeetingChoiceFromInt(acceptedTime));
        }
        return meetingChoices;
    }

    public boolean hasUserVoted(String meetingName, String username) {
        HashSet<Integer> userChoices = getUsersChoicesAsInt(meetingName, username);
        return !userChoices.isEmpty();
    }

    public boolean isMeetingFinalized(String meetingName) {
        ArrayList<String> usernames = GroupUserMappingDatabaseManager.
                instanceOfGroupUserMappingDatabaseManager(sqlDatabaseManager).getCurrentGroupUsernames();

        for (String username : usernames) {
            if (!hasUserVoted(meetingName, username)) {
                return false;
            }
        }
        return true;
    }
}


