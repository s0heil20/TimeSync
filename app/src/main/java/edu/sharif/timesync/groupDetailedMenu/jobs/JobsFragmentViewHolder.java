package edu.sharif.timesync.groupDetailedMenu.jobs;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;

public class JobsFragmentViewHolder extends  RecyclerView.ViewHolder{

    public TextView jobNameTextView;
    public LinearLayout linearLayout;
    public RelativeLayout mainLayout;

    public JobsFragmentViewHolder(View itemView) {
        super(itemView);
        jobNameTextView = itemView.findViewById(R.id.nameJobListMenu);
        linearLayout = itemView.findViewById(R.id.assignedUsersToJobLinearLayout);
        mainLayout = itemView.findViewById(R.id.jobsListItemLayout);
    }
}

