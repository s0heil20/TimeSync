package edu.sharif.timesync.groupDetailedMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.sharif.timesync.R;
import edu.sharif.timesync.groupListMenu.GroupListItem;
import edu.sharif.timesync.groupListMenu.GroupListMenuActivity;

public class GroupDetailedMenuActivity extends AppCompatActivity implements UsernameDialog.UsernameDialogListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detailed_menu);

        tabLayout = findViewById(R.id.mainTabLayout);
        viewPager = findViewById(R.id.mainViewPager);

        tabLayout.setupWithViewPager(viewPager);

        GroupDetailedMenuAdapter groupDetailedMenuAdapter = new GroupDetailedMenuAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        groupDetailedMenuAdapter.addFragment(new UsersFragment(), "USERS");
        groupDetailedMenuAdapter.addFragment(new JobsFragment(), "JOBS");
        groupDetailedMenuAdapter.addFragment(new DashboardFragment(), "DASHBOARD");
        groupDetailedMenuAdapter.addFragment(new MeetingFragment(), "MEETINGS");
        viewPager.setAdapter(groupDetailedMenuAdapter);

        // startActivity(new Intent(this, GroupListMenuActivity.class));
    }

    @Override
    public void addUser(String username) {
        // TODO add user!
        Toast.makeText(getBaseContext(), "ADDED " + username, Toast.LENGTH_SHORT).show();
    }


}
