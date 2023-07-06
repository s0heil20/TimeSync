package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import edu.sharif.timesync.entity.Meeting;

import edu.sharif.timesync.entity.MeetingState;
import edu.sharif.timesync.meeting.MeetingActivity;


public class MeetingFragment extends Fragment implements SelectMeetingListItemInterface{

    private RecyclerView recyclerView;
    private MeetingFragmentAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private boolean isLeader;
    private SQLDatabaseManager sqlDatabaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton = view.findViewById(R.id.floatingActionButtonMeetingListMenu);
        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        isLeader = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getAdminUsername().equals(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
        configureFloatingActionButton(view);
        recyclerView = view.findViewById(R.id.meetingListRecycleView);

        // TODO
        ArrayList<Meeting> meetings = sqlDatabaseManager.getMeetingDatabaseManager().getAllMeetingsOfCurrentGroup();
        addMeetingToRecyclerView(meetings);

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
                MeetingDialog dialog = new MeetingDialog();
                dialog.show(getChildFragmentManager(), "create dialog!");
            }
        });
    }

    public void addMeetingToRecyclerView(List<Meeting> meetings) {

        List<MeetingListItem> items = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (isLeader && (meeting.getMeetingState() == MeetingState.PENDING_NOT_VOTED || meeting.getMeetingState() == MeetingState.PENDING_VOTED)){
                items.add(new MeetingListItem(meeting.getName(), MeetingState.PENDING_VOTED));
            } else {
                items.add(new MeetingListItem(meeting.getName(), meeting.getMeetingState()));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MeetingFragmentAdapter(getContext(), items, false, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(MeetingListItem meetingListItem) {

            Intent intent = new Intent(getContext(), MeetingActivity.class);
            intent.putExtra("name", meetingListItem.getName());
            intent.putExtra("isLeader", isLeader);
            startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        addMeetingToRecyclerView(sqlDatabaseManager.getMeetingDatabaseManager().getAllMeetingsOfCurrentGroup());
    }
}