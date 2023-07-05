package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.sharif.timesync.R;

public class MeetingFragmentViewHolder extends RecyclerView.ViewHolder {
    public TextView meetingName;

    public LinearLayout mainLayout;

    public MeetingFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
        meetingName = itemView.findViewById(R.id.meetingListName);
        mainLayout = itemView.findViewById(R.id.meetingFragmentLinearLayout);
    }
}
