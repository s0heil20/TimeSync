package edu.sharif.timesync.groupListMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.Group;
import edu.sharif.timesync.groupDetailedMenu.GroupDetailedMenuActivity;
import edu.sharif.timesync.signUpSignIn.SignUpSignInActivity;

public class GroupListMenuActivity extends AppCompatActivity implements SelectGroupListItemInterface, GroupNameDialog.GroupDialogListener {

    private static final String loginFilename = "login";
    private static final String username = "username";
    private static final String password = "password";

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private GroupListAdapter adapter;
    private SQLDatabaseManager sqlDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_group_list_menu);
        ImageView signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getBaseContext());
                sqlDatabaseManager.getUserDatabaseManager().setLoggedInUser(null);
                SharedPreferences sharedPreferences = getSharedPreferences(loginFilename, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getBaseContext(), SignUpSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        TextView pageTitle = findViewById(R.id.pageTitle);
        pageTitle.setText("Groups");

        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);

        recyclerView = findViewById(R.id.groupListRecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        configureFloatingActionButton();

        addGroupToRecyclerView(sqlDatabaseManager.getGroupUserMappingDatabaseManager().getGroupNamesOfLoggedInUser());
    }

    private void configureFloatingActionButton() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "CLICKED ON FLOAT!", Toast.LENGTH_SHORT).show();
                GroupNameDialog dialog = new GroupNameDialog();
                dialog.show(getSupportFragmentManager(), "create dialog!");
            }
        });
    }

    private void addGroupToRecyclerView(List<String> groupNames) {
        // TODO!
        List<GroupListItem> items = new ArrayList<>();
        for (String groupName : groupNames) {
            items.add(new GroupListItem(groupName));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupListAdapter(this, items, sqlDatabaseManager.getGroupUserMappingDatabaseManager().getLoggedInUserAdminGroups(), this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(GroupListItem groupListItem) {
        // Toast.makeText(getBaseContext(), "TEST TOAST", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GroupDetailedMenuActivity.class);
        sqlDatabaseManager.getGroupUserMappingDatabaseManager().setCurrentGroup(groupListItem.getName());
        startActivity(intent);
    }

    @Override
    public void addGroup(String groupName) {
        boolean result = sqlDatabaseManager.getGroupUserMappingDatabaseManager().addGroupByName(new Group(groupName,
                sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername()));

        if (!result) {
            Toast.makeText(getBaseContext(), groupName + " ALREADY EXISTS", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "ADDED " + groupName, Toast.LENGTH_SHORT).show();
        }

        addGroupToRecyclerView(sqlDatabaseManager.getGroupUserMappingDatabaseManager().getGroupNamesOfLoggedInUser());
    }
}
