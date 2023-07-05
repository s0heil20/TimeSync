package edu.sharif.timesync.database;


import java.util.ArrayList;

import edu.sharif.timesync.entity.Job;

public class JobDatabaseManager {
    private static JobDatabaseManager jobDatabaseManager;
    private static final String TABLE_NAME = "JobDB";
    private static final String JOB_ID_FIELD = "job_id";
    private static final String ASSIGNED_USERS_FIELD = "assigned_users";

    private SQLDatabaseManager sqlDatabaseManager;

    public JobDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static JobDatabaseManager instanceOfJobDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (jobDatabaseManager == null) {
            jobDatabaseManager = new JobDatabaseManager(sqlDatabaseManager);
        }
        return jobDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(JOB_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ASSIGNED_USERS_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public void assignUsersToJobById(ArrayList<String> usernames, String jobId){

    }

    public ArrayList<Job> getJobsOfCurrentGroup() {
        return null;
    }

    public ArrayList<Job> getCurrentUsersJobs() {
        return null;
    }

}
