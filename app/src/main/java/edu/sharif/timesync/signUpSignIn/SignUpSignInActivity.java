package edu.sharif.timesync.signUpSignIn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.User;
import edu.sharif.timesync.groupListMenu.GroupListMenuActivity;

public class SignUpSignInActivity extends AppCompatActivity {

    private static final String fileName = "login";
    private static final String username = "username";
    private static final String password = "password";

    private SharedPreferences sharedPreferences;

    private ViewFlipper viewFlipper;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sign_in);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
//        if (sharedPreferences.contains(username)) {
//            String storedUsername = sharedPreferences.getString(username, "");
//            String storedPassword = sharedPreferences.getString(password, "");
//            SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);
//            User user = new User(storedUsername, storedPassword);
//            sqlDatabaseManager.getUserDatabaseManager().setLoggedInUser(user);
//            startActivity(new Intent(this, GroupListMenuActivity.class));
//        }

        viewFlipper = findViewById(R.id.viewFlipper);
    }

    public void goToSignIn(View view) {
        viewFlipper.showPrevious();
    }

    public void goToSignUp(View view) {
        viewFlipper.showNext();
    }

    public void trySignUp(View view) {
        TextInputLayout usernameInput = findViewById(R.id.signUpUsername);
        String username = Objects.requireNonNull(usernameInput.getEditText()).getText().toString();

        TextInputLayout passwordInput = findViewById(R.id.signUpPassword);
        String password = Objects.requireNonNull(passwordInput.getEditText()).getText().toString();

        TextInputLayout confirmPasswordInput = findViewById(R.id.signUpConfirmPassword);
        String confirmPassword = Objects.requireNonNull(confirmPasswordInput.getEditText()).getText().toString();

        boolean checkLengthResult = checkLength("username", username) && checkLength("password", password);
        boolean checkConfirmPasswordResult = checkConfirmPassword(password, confirmPassword);

        if (checkLengthResult && checkConfirmPasswordResult) {
            User user = new User(username, password);
            SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);
            boolean doesUsernameExist = sqlDatabaseManager.getUserDatabaseManager().doesUsernameExists(user);

            if (doesUsernameExist) {
                Toast.makeText(this, "username already exists.", Toast.LENGTH_LONG).show();
            } else {
                sqlDatabaseManager.getUserDatabaseManager().signUpUser(user);
                viewFlipper.showNext();
            }
        }
    }

    private boolean checkLength(String fieldName, String content) {
        if (content.length() < 6 || content.length() > 30) {
            Toast.makeText(this, fieldName + " should be between 6 and 30 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkConfirmPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        Toast.makeText(this, "password and confirm password don't match.", Toast.LENGTH_LONG).show();
        return false;
    }

    public void trySignIn(View view) {
        TextInputLayout usernameInput = findViewById(R.id.signInUsername);
        String username = Objects.requireNonNull(usernameInput.getEditText()).getText().toString();

        TextInputLayout passwordInput = findViewById(R.id.signInPassword);
        String password = Objects.requireNonNull(passwordInput.getEditText()).getText().toString();

        CheckBox rememberInput = findViewById(R.id.signInRemember);
        boolean remember = rememberInput.isChecked();

        User user = new User(username, password);
        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);

        if (sqlDatabaseManager.getUserDatabaseManager().doesUsernameExists(user)) {
            if (sqlDatabaseManager.getUserDatabaseManager().checkPassword(user)) {
                if (remember) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SignUpSignInActivity.username, username);
                    editor.putString(SignUpSignInActivity.password, password);
                    editor.apply();
                }

                sqlDatabaseManager.getUserDatabaseManager().setLoggedInUser(user);

                Intent intent = new Intent(this, GroupListMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "incorrect password.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "username does not exist.", Toast.LENGTH_LONG).show();
        }
    }
}
