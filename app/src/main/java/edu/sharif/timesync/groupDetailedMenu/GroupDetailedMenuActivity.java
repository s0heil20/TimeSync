package edu.sharif.timesync.groupDetailedMenu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.groupDetailedMenu.dashboard.DashboardFragment;
import edu.sharif.timesync.groupDetailedMenu.jobs.JobDialog;
import edu.sharif.timesync.groupDetailedMenu.jobs.JobsFragment;
import edu.sharif.timesync.groupDetailedMenu.meeting.MeetingFragment;
import edu.sharif.timesync.groupDetailedMenu.user.UsernameDialog;
import edu.sharif.timesync.groupDetailedMenu.user.UsersFragment;

public class GroupDetailedMenuActivity extends AppCompatActivity implements UsernameDialog.UsernameDialogListener, JobDialog.jobDialogListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLDatabaseManager sqlDatabaseManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detailed_menu);
        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);

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
        try {
            sqlDatabaseManager.getGroupUserMappingDatabaseManager().addUserToCurrentGroup(username);
        } catch (Exception e) {
//            toast
        }
        Toast.makeText(getBaseContext(), "Added " + username , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addJob(String name) {
        sqlDatabaseManager.getGroupJobMappingDatabaseManager().addJobByName(name);

    }
}
