package edu.sharif.timesync.groupDetailedMenu.jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;

public class JobsFragmentAdapter extends RecyclerView.Adapter<JobsFragmentViewHolder>{
    private Context context;
    private List<JobListItem> items;
    private SelectJobsListItemInterface listener;
    private boolean isLeader;

    private LayoutInflater layoutInflater;


    public JobsFragmentAdapter(Context context, List<JobListItem> items, boolean isLeader, SelectJobsListItemInterface listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.isLeader = isLeader;

        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public JobsFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JobsFragmentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_jobs_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(JobsFragmentViewHolder holder, int position) {
        holder.jobNameTextView.setText(items.get(position).getName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(holder.getAdapterPosition()));
            }
        });
        for (String assignedUser : items.get(position).getAssignedUsers()) {
            holder.linearLayout.addView(createUsernameView(assignedUser));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public View createUsernameView(String name) {
        View view = this.layoutInflater.inflate(R.layout.tag_users_of_job,null, false);
        TextView usernameTextView = view.findViewById(R.id.usernameTagTextView);
        usernameTextView.setText(name);
        return view;
    }
}
