package edu.sharif.timesync.groupDetailedMenu.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sharif.timesync.R;


public class UsersFragmentAdapter extends RecyclerView.Adapter<UsersFragmentViewHolder> {
    private Context context;
    private List<UserListItem> items;
    private String leaderUsername;


    public UsersFragmentAdapter(Context context, List<UserListItem> items, String leaderUsername) {
        this.context = context;
        this.items = items;
        this.leaderUsername = leaderUsername;
    }

    @Override
    public UsersFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersFragmentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_users_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(UsersFragmentViewHolder holder, int position) {
        holder.usernameTextView.setText(items.get(position).getUsername());
        if(!items.get(position).getUsername().equals(this.leaderUsername)) {
            System.out.println("username " + items.get(position).getUsername() );
            System.out.println("leader " + this.leaderUsername);
            holder.isLeaderImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
