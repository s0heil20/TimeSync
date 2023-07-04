package edu.sharif.timesync.groupDetailedMenu;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.sharif.timesync.R;

public class JobsFragmentViewHolder extends  RecyclerView.ViewHolder{

    public TextView jobNameTextView;
    public RelativeLayout mainLayout;

    public JobsFragmentViewHolder(View itemView) {
        super(itemView);
        jobNameTextView = itemView.findViewById(R.id.nameJobListMenu);
        mainLayout = itemView.findViewById(R.id.jobsListItemLayout);
    }
}
