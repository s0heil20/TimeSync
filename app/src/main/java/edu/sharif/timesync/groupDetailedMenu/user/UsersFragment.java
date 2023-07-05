package edu.sharif.timesync.groupDetailedMenu.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.User;


public class UsersFragment extends Fragment  {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private UsersFragmentAdapter adapter;
    private SQLDatabaseManager sqlDatabaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());

        recyclerView = view.findViewById(R.id.usersListRecyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButtonUsersListMenu);

        configureFloatingActionButton(view);


        addUserToRecyclerView(sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroupUsernames());
    }

    private void configureFloatingActionButton(View view){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"CLICKED ON FLOAT!",Toast.LENGTH_SHORT).show();
                UsernameDialog dialog = new UsernameDialog();
                dialog.show(getChildFragmentManager(), "create dialog!");
            }
        });
    }

    private void addUserToRecyclerView(List<String> userList) {
        List<UserListItem> items = new ArrayList<>();
        for (String user : userList) {
            items.add(new UserListItem(user));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UsersFragmentAdapter(getContext(), items, sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getAdminUsername());
        recyclerView.setAdapter(adapter);
    }


}