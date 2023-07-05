package edu.sharif.timesync.groupListMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListViewHolder>{

    private Context context;
    private List<GroupListItem> items;
    private SelectGroupListItemInterface listener;
    private List<String> groupAdmins;

    public GroupListAdapter(Context context, List<GroupListItem> items, List<String> groupAdmins, SelectGroupListItemInterface listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.groupAdmins = groupAdmins;
    }

    @NonNull
    @Override
    public GroupListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(GroupListViewHolder holder, int position) {
        holder.groupNameTextView.setText(items.get(position).getName());
        if(!groupAdmins.contains(items.get(position).getName())) {
            holder.isLeaderImageView.setVisibility(View.INVISIBLE);
        }
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
