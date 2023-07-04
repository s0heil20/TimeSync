package edu.sharif.timesync.groupDetailedMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;


public class UsersFragmentAdapter extends RecyclerView.Adapter<UsersFragmentViewHolder> {
    private Context context;
    private List<UserListItem> items;


    public UsersFragmentAdapter(Context context, List<UserListItem> items, boolean isLeader) {
        this.context = context;
        this.items = items;
    }

    @Override
    public UsersFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersFragmentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_users_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(UsersFragmentViewHolder holder, int position) {
        holder.usernameTextView.setText(items.get(position).getUsername());
        // TODO hide image if leader!
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
