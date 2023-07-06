package edu.sharif.timesync.assignUserToJob;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.User;


public class AssignUserToJobActivity extends AppCompatActivity {

    private ListView usersList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> usernames = new ArrayList<>();
    private String jobName;
    private Button submitButton;
    private SQLDatabaseManager sqlDatabaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("name");
            jobName = value;
        }

        List<String> currentGroupUsers = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroupUsernames();
        for (String currentGroupUser : currentGroupUsers) {
            if (!currentGroupUser.equals(sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getAdminUsername())) {
                usernames.add(currentGroupUser);
            }
        }

        setContentView(R.layout.activity_assign_user_to_job);
        usersList = findViewById(R.id.usersListViewAssignUserToJobMenu);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,usernames);
        usersList.setAdapter(adapter);
        configureSubmitButton();

    }

    private void configureSubmitButton(){
        submitButton = findViewById(R.id.submitButtonAssignUserToJobMenu);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> usersSelected = new ArrayList<>();
                for (int i = 0;i < usersList.getCount();i++){
                    if(usersList.isItemChecked(i)) {
                        usersSelected.add(usersList.getItemAtPosition(i)+"");
                    }
                }
                sqlDatabaseManager.getJobDatabaseManager().assignUsersToJobByName(usersSelected, jobName);
                Toast.makeText(getBaseContext(), "Users Added", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
