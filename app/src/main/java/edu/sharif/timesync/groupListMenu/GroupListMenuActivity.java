package edu.sharif.timesync.groupListMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class GroupListMenuActivity extends AppCompatActivity implements SelectGroupListItemInterface, GroupNameDialog.GroupDialogListener {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private GroupListAdapter adapter;
    private SQLDatabaseManager sqlDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list_menu);
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
        boolean result = sqlDatabaseManager.getGroupUserMappingDatabaseManager().addGroupByName(new Group(groupName, sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser()));

        if (!result) {
            Toast.makeText(getBaseContext(), groupName + " ALREADY EXISTS", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "ADDED " + groupName, Toast.LENGTH_SHORT).show();
        }

        addGroupToRecyclerView(sqlDatabaseManager.getGroupUserMappingDatabaseManager().getGroupNamesOfLoggedInUser());
    }
}
