package edu.sharif.timesync.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLDatabaseManager extends SQLiteOpenHelper {

    private static SQLDatabaseManager sqlDatabaseManager;
    private static final String DATABASE_NAME = "AppDB";
    private static final int DATABASE_VERSION = 1;

    private final UserDatabaseManager userDatabaseManager;
    private final GroupDatabaseManager groupDatabaseManager;


    public SQLDatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        userDatabaseManager = UserDatabaseManager.instanceOfUserDatabaseManager(this);
        groupDatabaseManager = GroupDatabaseManager.instanceOfGroupDatabaseManager(this);
    }

    public static SQLDatabaseManager instanceOfDatabase(Context context) {
        if (sqlDatabaseManager == null) {
            sqlDatabaseManager = new SQLDatabaseManager(context);
        }
        return sqlDatabaseManager;
    }

    public UserDatabaseManager getUserDatabaseManager() {
        return userDatabaseManager;
    }

    public GroupDatabaseManager getGroupDatabaseManager() {
        return groupDatabaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(userDatabaseManager.createTableString());
        sqLiteDatabase.execSQL(groupDatabaseManager.createTableString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + userDatabaseManager.getTableName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + groupDatabaseManager.getTableName());
        onCreate(sqLiteDatabase);
    }

    public void dropTables() {
        SQLiteDatabase sqLiteDatabase = sqlDatabaseManager.getWritableDatabase();
        onUpgrade(sqLiteDatabase, 1, 2);
    }

}