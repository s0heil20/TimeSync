package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.groupDetailedMenu.jobs.JobListItem;
import edu.sharif.timesync.groupDetailedMenu.jobs.JobsFragmentViewHolder;
import edu.sharif.timesync.groupDetailedMenu.jobs.SelectJobsListItemInterface;

public class MeetingFragmentAdapter extends RecyclerView.Adapter<MeetingFragmentViewHolder> {
    private Context context;
    private List<MeetingListItem> items;
    private SelectMeetingListItemInterface listener;


    public MeetingFragmentAdapter(Context context, List<MeetingListItem> items, boolean isLeader, SelectMeetingListItemInterface listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MeetingFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeetingFragmentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_meeting_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MeetingFragmentViewHolder holder, int position) {
        holder.meetingName.setText(items.get(position).getMeeting().getName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
