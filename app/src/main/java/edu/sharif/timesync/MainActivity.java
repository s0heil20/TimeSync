package edu.sharif.timesync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.sharif.timesync.groupDetailedMenu.GroupDetailedMenuActivity;
import edu.sharif.timesync.groupDetailedMenu.GroupDetailedMenuAdapter;
import edu.sharif.timesync.groupListMenu.GroupListMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, GroupDetailedMenuActivity.class));
    }
}