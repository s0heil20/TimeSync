package edu.sharif.timesync.groupListMenu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.entity.Group;

public class GroupListMenuActivity extends AppCompatActivity implements SelectGroupListItemInterface{

    private RecyclerView recyclerView;
    private GroupListAdapter adapter;
    private ArrayList<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list_menu);

        recyclerView = findViewById(R.id.groupListRecyclerView);
        addGroupToRecyclerView(new ArrayList<>());
    }

    private void addGroupToRecyclerView(List<Group> groupList) {
        // TODO
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
}
