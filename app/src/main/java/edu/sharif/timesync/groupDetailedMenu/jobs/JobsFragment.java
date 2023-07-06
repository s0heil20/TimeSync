package edu.sharif.timesync.groupDetailedMenu.jobs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.assignUserToJob.AssignUserToJobActivity;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.Job;
import edu.sharif.timesync.groupDetailedMenu.user.UsernameDialog;
import edu.sharif.timesync.workOnJob.WorkOnJobActivity;

public class JobsFragment extends Fragment implements SelectJobsListItemInterface {

    private RecyclerView recyclerView;
    private JobsFragmentAdapter adapter;
    private FloatingActionButton floatingActionButton;

    private SQLDatabaseManager sqlDatabaseManager;
    private boolean isLeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());

        isLeader = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getAdminUsername().equals(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());

        floatingActionButton = view.findViewById(R.id.floatingActionButtonJobsListMenu);

        recyclerView = view.findViewById(R.id.jobsListRecyclerView);

        configureFloatingActionButton(view);

        if (isLeader) {
            addJobToRecyclerView(sqlDatabaseManager.getGroupJobMappingDatabaseManager().getJobsOfCurrentGroup());
        } else {
            addJobToRecyclerView(sqlDatabaseManager.getJobDatabaseManager().getCurrentUsersJobs());
        }
    }

    private void configureFloatingActionButton(View view) {
        if (!isLeader) {
            floatingActionButton.hide();
            return;
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"CLICKED ON FLOAT!",Toast.LENGTH_SHORT).show();
                JobDialog dialog = new JobDialog();
                dialog.show(getChildFragmentManager(), "create dialog!");
            }
        });
    }


    public void addJobToRecyclerView(List<String> jobList) {
        List<JobListItem> items = new ArrayList<>();
        for (String job : jobList) {
            items.add(new JobListItem(job,sqlDatabaseManager.getJobDatabaseManager().getJobUsers(job)) );
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JobsFragmentAdapter(getContext(), items, isLeader, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(JobListItem jobListItem) {
        if(isLeader) {
            Intent intent = new Intent(getContext(), AssignUserToJobActivity.class);
            intent.putExtra("name", jobListItem.getName());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), WorkOnJobActivity.class);
            intent.putExtra("name", jobListItem.getName());
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLeader) {
            addJobToRecyclerView(sqlDatabaseManager.getGroupJobMappingDatabaseManager().getJobsOfCurrentGroup());
        } else {
            addJobToRecyclerView(sqlDatabaseManager.getJobDatabaseManager().getCurrentUsersJobs());
        }
    }
}