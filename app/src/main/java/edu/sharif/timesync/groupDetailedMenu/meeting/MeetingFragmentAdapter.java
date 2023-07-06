package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.entity.MeetingState;


public class MeetingFragmentAdapter extends RecyclerView.Adapter<MeetingFragmentViewHolder> {
    private Context context;
    private List<MeetingListItem> items;
    private SelectMeetingListItemInterface listener;
    private boolean isLeader;


    public MeetingFragmentAdapter(Context context, List<MeetingListItem> items, boolean isLeader, SelectMeetingListItemInterface listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.isLeader = isLeader;
    }

    @Override
    public MeetingFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeetingFragmentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_meeting_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MeetingFragmentViewHolder holder, int position) {
        holder.meetingName.setText(items.get(position).getName());

        switch (items.get(position).getMeetingState()){
            case FINALIZED:
                holder.meetingStateImageView.setImageResource(R.drawable.done_svgrepo_com);
                break;
            case PENDING_VOTED:
                holder.meetingStateImageView.setImageResource(R.drawable.pending_icon_clock);
                break;
            case PENDING_NOT_VOTED:
                holder.meetingStateImageView.setImageResource(R.drawable.vote_svgrepo_com);
                break;
        }
        if(items.get(position).getMeetingState() != MeetingState.PENDING_VOTED) {
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(items.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
