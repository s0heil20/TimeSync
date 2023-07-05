package edu.sharif.timesync.assignUserToJob;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.sharif.timesync.R;


public class AssignUserToJobActivity extends AppCompatActivity {

    private ListView usersList;
    private ArrayAdapter<String> adapter;
    private String[] usernames = {"user1", "user2"};
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String usersSelected = "Users selected: \n";
                for (int i = 0;i < usersList.getCount();i++){
                    if(usersList.isItemChecked(i)) {
                        usersSelected += usersList.getItemAtPosition(i)+ " ";
                    }
                }
                Toast.makeText(getBaseContext(), usersSelected, Toast.LENGTH_LONG).show();
            }
        });
    }
}
