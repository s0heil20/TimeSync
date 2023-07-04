package edu.sharif.timesync.groupDetailedMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.groupListMenu.GroupListItem;
import edu.sharif.timesync.groupListMenu.GroupListViewHolder;
import edu.sharif.timesync.groupListMenu.SelectGroupListItemInterface;

public class UsersFragmentViewHolder extends RecyclerView.ViewHolder {
    public ImageView isLeaderImageView;
    public TextView usernameTextView;
    public RelativeLayout mainLayout;

    public UsersFragmentViewHolder(View itemView) {
        super(itemView);
        isLeaderImageView = itemView.findViewById(R.id.isLeaderIconUsersMenu);
        usernameTextView = itemView.findViewById(R.id.usernameUsersListMenu);
        mainLayout = itemView.findViewById(R.id.usersListItemLayout);
    }
}
