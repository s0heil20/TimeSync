package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.sharif.timesync.entity.Group;

public class GroupUserMappingDatabaseManager implements EntityDatabaseManager {
    private static GroupUserMappingDatabaseManager groupUserMappingDatabaseManager;
    private static final String TABLE_NAME = "GroupUserDB";

    private static final String ID_FIELD = "id";

    private static final String USERNAME_FIELD = "username";
    private static final String GROUP_NAME_FIELD = "group_name";
    private static final String IS_ADMIN_FIELD = "is_admin";

    private Group currentGroup;


    private SQLDatabaseManager sqlDatabaseManager;

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(String currentGroup) {
        // TODO
        //this.currentGroup = currentGroup;
    }

    public GroupUserMappingDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
    }

    public static GroupUserMappingDatabaseManager instanceOfGroupUserMappingDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (groupUserMappingDatabaseManager == null) {
            groupUserMappingDatabaseManager = new GroupUserMappingDatabaseManager(sqlDatabaseManager);
        }
        return groupUserMappingDatabaseManager;
    }


    @Override
    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USERNAME_FIELD)
                .append(" TEXT, ")
                .append(GROUP_NAME_FIELD)
                .append(" TEXT, ")
                .append(IS_ADMIN_FIELD)
                .append(" INT)");


        return sql.toString();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    private boolean doesGroupExist(Group group) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(GROUP_NAME_FIELD)
                .append(" = ? ");


        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{group.getName()});
        return result.getCount() > 0;
    }

    public boolean addGroupByName(Group group) {
        if (doesGroupExist(group))
            return false;

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_NAME_FIELD, group.getName());
        contentValues.put(USERNAME_FIELD, group.getAdminUser().getUsername());

        contentValues.put(IS_ADMIN_FIELD, 1);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<String> getGroupNamesByUsername(String username) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USERNAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{username});

        ArrayList<String> groupNames = new ArrayList<>();
        while (result.moveToNext()) {
            groupNames.add(result.getString(2));
        }
        return groupNames;
    }

    public ArrayList<String> getGroupNamesOfLoggedInUser() {
        String loggedInUsername = UserDatabaseManager.instanceOfUserDatabaseManager(sqlDatabaseManager)
                .getLoggedInUser().getUsername();
        return getGroupNamesByUsername(loggedInUsername);
    }

    public boolean isLoggedInUserAdminInCurrentGroup() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();
        String loggedInUsername = UserDatabaseManager.instanceOfUserDatabaseManager(sqlDatabaseManager)
                .getLoggedInUser().getUsername();
        String currentGroupName = currentGroup.getName();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USERNAME_FIELD)
                .append(" = ? AND ")
                .append(GROUP_NAME_FIELD)
                .append(" = ?");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{loggedInUsername, currentGroupName});

        result.moveToFirst();
        return result.getInt(3) == 1;
    }

    public ArrayList<String> getCurrentGroupUsernames() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();
        String currentGroupName = currentGroup.getName();
        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(GROUP_NAME_FIELD)
                .append(" = ? ");

        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{currentGroupName});

        ArrayList<String> usernames = new ArrayList<>();
        while (result.moveToNext()) {
            usernames.add(result.getString(1));
        }
        return usernames;
    }

    public void addUserToCurrentGroup(String username) {
        String currentGroupName = currentGroup.getName();

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_FIELD, username);
        contentValues.put(GROUP_NAME_FIELD, currentGroupName);
        contentValues.put(IS_ADMIN_FIELD, 0);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

    }

    public ArrayList<String> getLoggedInUserAdminGroups(){
        return null;
    }


}
