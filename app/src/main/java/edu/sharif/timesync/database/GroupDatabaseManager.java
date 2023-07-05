package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import edu.sharif.timesync.entity.Group;
import edu.sharif.timesync.entity.User;

public class GroupDatabaseManager implements EntityDatabaseManager {
    private static GroupDatabaseManager groupDatabaseManager;
    private static final String TABLE_NAME = "GroupDB";
    private static final String GROUP_ID_FIELD = "group_id";
    private static final String NAME_FIELD = "name";
    private static final String ADMIN_USERNAME_FIELD = "admin_username";
    private static final String USERS_FIELD = "users";
    private static final String JOBS_FIELD = "jobs";

    private SQLDatabaseManager sqlDatabaseManager;

    public GroupDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static GroupDatabaseManager instanceOfGroupDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (groupDatabaseManager == null) {
            groupDatabaseManager = new GroupDatabaseManager(sqlDatabaseManager);
        }
        return groupDatabaseManager;
    }

    private boolean doesGroupExist(Group group) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(NAME_FIELD)
                .append(" = ? ");


        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{group.getName()});
        return result.getCount() > 0;
    }

    public boolean addGroupByName(Group group){

        if (doesGroupExist(group))
            return false;

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_FIELD, group.getName());
        contentValues.put(ADMIN_USERNAME_FIELD, group.getAdminUser().getUsername());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
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
                .append(NAME_FIELD)
                .append(" TEXT NOT NULL UNIQUE, ")
                .append(ADMIN_USERNAME_FIELD)
                .append(" TEXT NOT NULL, ")
                .append(USERS_FIELD)
                .append(" TEXT, ")
                .append(JOBS_FIELD)
                .append(" TEXT)");

        return sql.toString();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public ArrayList<Group> getGroupsByUsername(String username){
        return null;
    }

    public ArrayList<Group> getGroupsOfLoggedInUser(){
        return null;
    }

    public String getLoggedInUserRoleInCurrentGroup() {
//        "user" or "leader"
        return null;
    }

    public ArrayList<User> getCurrentGroupUsers() {
        return null;
    }

    public void addUserToCurrentGroup(String username){

    }
}
