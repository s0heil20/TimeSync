package edu.sharif.timesync.groupListMenu;

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
import edu.sharif.timesync.entity.Group;

public class GroupListMenuActivity extends AppCompatActivity implements SelectGroupListItemInterface, GroupNameDialog.GroupDialogListener {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private GroupListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list_menu);

        recyclerView = findViewById(R.id.groupListRecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        configureFloatingActionButton();

        addGroupToRecyclerView(new ArrayList<>());
    }

    private void configureFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO!
                Toast.makeText(getBaseContext(),"CLICKED ON FLOAT!",Toast.LENGTH_SHORT).show();
                GroupNameDialog dialog = new GroupNameDialog();
                dialog.show(getSupportFragmentManager(), "create dialog!");
            }
        });
    }

    private void addGroupToRecyclerView(List<Group> groupList) {
        // TODO!
        List<GroupListItem> items = new ArrayList<>();
        items.add(new GroupListItem("TESTING", 1234));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupListAdapter(this, items, false, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(GroupListItem groupListItem) {
        Toast.makeText(getBaseContext(), "TEST TOAST", Toast.LENGTH_SHORT).show();
        // TODO start next activity
    }

    @Override
    public void addGroup(String groupName) {
        // TODO add group!
        Toast.makeText(getBaseContext(), "ADDED " + groupName, Toast.LENGTH_SHORT).show();
    }
}
