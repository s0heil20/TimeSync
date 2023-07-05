package edu.sharif.timesync.groupDetailedMenu.jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;

public class JobsFragmentAdapter extends RecyclerView.Adapter<JobsFragmentViewHolder>{
    private Context context;
    private List<JobListItem> items;
    private SelectJobsListItemInterface listener;


    public JobsFragmentAdapter(Context context, List<JobListItem> items, boolean isLeader, SelectJobsListItemInterface listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
