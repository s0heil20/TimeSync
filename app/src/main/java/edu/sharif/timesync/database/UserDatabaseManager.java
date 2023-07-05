package edu.sharif.timesync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.sharif.timesync.entity.User;

public class UserDatabaseManager {
    private static UserDatabaseManager userDatabaseManager;
    private static final String TABLE_NAME = "UserDB";
    private static final String ID_FIELD = "_id";
    private static final String USER_NAME_FIELD = "user_name";
    private static final String PASSWORD_FIELD = "password";

    private User loggedInUser;

    private SQLDatabaseManager sqlDatabaseManager;

    private UserDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        this.sqlDatabaseManager = sqlDatabaseManager;
        this.loggedInUser = null;
    }

    public static UserDatabaseManager instanceOfUserDatabaseManager(SQLDatabaseManager sqlDatabaseManager) {
        if (userDatabaseManager == null) {
            userDatabaseManager = new UserDatabaseManager(sqlDatabaseManager);
        }
        return userDatabaseManager;
    }

    public String createTableString() {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USER_NAME_FIELD)
                .append(" TEXT NOT NULL UNIQUE, ")
                .append(PASSWORD_FIELD)
                .append(" TEXT) ");

        return sql.toString();
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public boolean signUpUser(User user) {
        if (doesUsernameExists(user))
            return false;

        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME_FIELD, user.getUsername());
        contentValues.put(PASSWORD_FIELD, user.getPassword());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean doesUsernameExists(User user) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USER_NAME_FIELD)
                .append(" = ? ");


        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{user.getUsername()});
        return result.getCount() > 0;

    }

    public boolean checkPassword(User user) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getReadableDatabase();

        StringBuilder sql;
        sql = new StringBuilder()
                .append("SELECT * FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USER_NAME_FIELD)
                .append(" = ? ");


        Cursor result = sqLiteDatabase.rawQuery(sql.toString(), new String[]{user.getUsername()});
        result.moveToFirst();
        String password = result.getString(2);
        return password.equals(user.getPassword());
    }


    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public void changePassword(User user) {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, USER_NAME_FIELD + "=?", new String[]{user.getUsername()});
        sqLiteDatabase.close();

        getLoggedInUser().setPassword(user.getPassword());
        signUpUser(user);
    }

    public void deleteLoggedInUser() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, USER_NAME_FIELD + "=?", new String[]{getLoggedInUser().getUsername()});
        sqLiteDatabase.close();
    }

}
