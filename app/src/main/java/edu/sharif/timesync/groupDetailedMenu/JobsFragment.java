package edu.sharif.timesync.groupDetailedMenu;

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
import edu.sharif.timesync.entity.Job;
import edu.sharif.timesync.entity.User;

public class JobsFragment extends Fragment implements SelectJobsListItemInterface {

    private RecyclerView recyclerView;
    private JobsFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.jobsListRecyclerView);


        addJobToRecyclerView(new ArrayList<>());
    }


    private void addJobToRecyclerView(List<Job> jobList) {
        // TODO!
        List<JobListItem> items = new ArrayList<>();
        items.add(new JobListItem("TESTING Job"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JobsFragmentAdapter(getContext(), items, false, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(JobListItem jobListItem) {
        Toast.makeText(getContext(), "TEST TOAST", Toast.LENGTH_SHORT).show();
        // TODO start next activity
    }
}