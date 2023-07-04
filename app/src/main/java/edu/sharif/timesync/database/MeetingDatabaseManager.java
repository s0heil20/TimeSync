package edu.sharif.timesync.database;

public class MeetingDatabaseManager {

    private static MeetingDatabaseManager meetingDatabaseManager;
    private static final String TABLE_NAME = "MeetingDB";
    private static final String MEETING_ID_FIELD = "meeting_id";
    private static final String GROUP_ID_FIELD = "group_id";
    private static final String CANDIDATE_TIMES_FIELD = "candidate_times";
    private static final String AVAILABLE_TIMES_FOR_USERS_FIELD = "available_times_for_users";

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
                .append(GROUP_ID_FIELD)
                .append(" INT, ")
                .append(CANDIDATE_TIMES_FIELD)
                .append(" TEXT, ")
                .append(AVAILABLE_TIMES_FOR_USERS_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}
