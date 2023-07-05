package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.entity.Job;
import edu.sharif.timesync.entity.Meeting;
import edu.sharif.timesync.groupDetailedMenu.jobs.JobListItem;
import edu.sharif.timesync.groupDetailedMenu.jobs.JobsFragmentAdapter;

public class MeetingFragment extends Fragment implements SelectMeetingListItemInterface{

    private RecyclerView recyclerView;
    private MeetingFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.meetingFragmentRecyclerView);


        addJobToRecyclerView(new ArrayList<>());
    }

    private void addJobToRecyclerView(List<Meeting> meetings) {

        List<MeetingListItem> items = new ArrayList<>();

        for (Meeting meeting : meetings) {
            items.add(new MeetingListItem(meeting));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MeetingFragmentAdapter(getContext(), items, false, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(MeetingListItem groupListItem) {

    }
}