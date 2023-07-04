package edu.sharif.timesync.database;


public class TimeSpentDatabaseManager {
    private static TimeSpentDatabaseManager timeSpentDatabaseManager;
    private static final String TABLE_NAME = "TimeSpentDB";
    private static final String ID_FIELD = "id";
    private static final String USER_FIELD = "user";
    private static final String JOB_FIELD = "job";
    private static final String TIME_LENGTH_FIELD = "time_length";

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
                .append(USER_FIELD)
                .append(" TEXT, ")
                .append(JOB_FIELD)
                .append(" TEXT, ")
                .append(TIME_LENGTH_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}
