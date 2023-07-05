package edu.sharif.timesync.database;

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
                .append(" TEXT NOT NULL UNIQUE, ")
                .append(JOB_NAME_FIELD)
                .append(" TEXT NOT NULL, ");

        return sql.toString();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

}
