package edu.sharif.timesync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.sharif.timesync.assignUserToJob.AssignUserToJobActivity;
import edu.sharif.timesync.meeting.MeetingActivity;

import edu.sharif.timesync.groupDetailedMenu.GroupDetailedMenuActivity;
import edu.sharif.timesync.groupDetailedMenu.GroupDetailedMenuAdapter;
import edu.sharif.timesync.groupListMenu.GroupListMenuActivity;
import edu.sharif.timesync.signUpSignIn.SignUpSignInActivity;
import edu.sharif.timesync.workOnJob.WorkOnJobActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(this, MeetingActivity.class));
       startActivity(new Intent(this, GroupDetailedMenuActivity.class));
//        startActivity(new Intent(this, AssignUserToJobActivity.class));
//        startActivity(new Intent(this, WorkOnJobActivity.class));
    }
}