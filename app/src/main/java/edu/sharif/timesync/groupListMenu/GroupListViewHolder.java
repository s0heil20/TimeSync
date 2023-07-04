package edu.sharif.timesync.groupListMenu;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.sharif.timesync.R;

public class GroupListViewHolder extends RecyclerView.ViewHolder {

    public ImageView isLeaderImageView;
    public TextView groupNameTextView;
    public TextView groupIdTextView;
    public RelativeLayout mainLayout;

    public GroupListViewHolder(View itemView) {
        super(itemView);
        isLeaderImageView = itemView.findViewById(R.id.isLeaderIcon);
        groupNameTextView = itemView.findViewById(R.id.groupName);
        groupIdTextView = itemView.findViewById(R.id.groupId);
        mainLayout = itemView.findViewById(R.id.groupListItemLayout);
    }
}
