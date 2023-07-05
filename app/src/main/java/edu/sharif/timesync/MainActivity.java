package edu.sharif.timesync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.User;
import edu.sharif.timesync.groupListMenu.GroupListMenuActivity;
import edu.sharif.timesync.signUpSignIn.SignUpSignInActivity;

public class MainActivity extends AppCompatActivity {

    private static final String loginFilename = "login";
    private static final String username = "username";
    private static final String password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(loginFilename, Context.MODE_PRIVATE);
//        if (sharedPreferences.contains(username)) {
//            String storedUsername = sharedPreferences.getString(username, "");
//            String storedPassword = sharedPreferences.getString(password, "");
//            SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);
//            User user = new User(storedUsername, storedPassword);
//            sqlDatabaseManager.getUserDatabaseManager().setLoggedInUser(user);
//
//            Intent intent = new Intent(this, GroupListMenuActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        } else {
            Intent intent = new Intent(this, SignUpSignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
//        }
    }
}