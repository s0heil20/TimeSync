package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.sharif.timesync.R;

public class MeetingFragmentViewHolder extends RecyclerView.ViewHolder {
    public TextView meetingName;

    public RelativeLayout mainLayout;

    public MeetingFragmentViewHolder(View itemView) {
        super(itemView);
        meetingName = itemView.findViewById(R.id.nameMeetingTextView);
        mainLayout = itemView.findViewById(R.id.meetingListItemLayout);
    }
}
