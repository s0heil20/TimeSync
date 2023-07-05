package edu.sharif.timesync.groupDetailedMenu.jobs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.assignUserToJob.AssignUserToJobActivity;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.entity.Job;

public class JobsFragment extends Fragment implements SelectJobsListItemInterface {

    private RecyclerView recyclerView;
    private JobsFragmentAdapter adapter;
    private SQLDatabaseManager sqlDatabaseManager;

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

        recyclerView = view.findViewById(R.id.jobsListRecyclerView);


        // TODO!
        addJobToRecyclerView(sqlDatabaseManager.getJobDatabaseManager().getCurrentUsersJobs());
    }


    private void addJobToRecyclerView(List<Job> jobList) {
        // TODO!
        List<JobListItem> items = new ArrayList<>();
        for (Job job : jobList) {
            items.add(new JobListItem(job.getName()));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JobsFragmentAdapter(getContext(), items, false, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(JobListItem jobListItem) {
        Intent intent = new Intent(getContext(), AssignUserToJobActivity.class);
        intent.putExtra("name", jobListItem.getName());
        startActivity(intent);
    }
}