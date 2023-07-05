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
    private UsersFragment userFragment;
    private JobsFragment jobsFragment;
    private DashboardFragment dashboardFragment;
    private MeetingFragment meetingFragment;
    private boolean isLeader;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detailed_menu);
        sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);
        isLeader = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroup().getAdminUsername() == sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername();

        tabLayout = findViewById(R.id.mainTabLayout);
        viewPager = findViewById(R.id.mainViewPager);

        tabLayout.setupWithViewPager(viewPager);

        GroupDetailedMenuAdapter groupDetailedMenuAdapter = new GroupDetailedMenuAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        userFragment = new UsersFragment();
        jobsFragment = new JobsFragment();
        dashboardFragment = new DashboardFragment();
        meetingFragment = new MeetingFragment();


        groupDetailedMenuAdapter.addFragment(userFragment, "USERS");
        groupDetailedMenuAdapter.addFragment(jobsFragment, "JOBS");
        groupDetailedMenuAdapter.addFragment(dashboardFragment, "DASHBOARD");
        groupDetailedMenuAdapter.addFragment(meetingFragment, "MEETINGS");
        viewPager.setAdapter(groupDetailedMenuAdapter);

        // startActivity(new Intent(this, GroupListMenuActivity.class));
    }

    @Override
    public void addUser(String username) {
        // TODO add user!
        try {
            sqlDatabaseManager.getGroupUserMappingDatabaseManager().addUserToCurrentGroup(username);
            Toast.makeText(getBaseContext(), "Added " + username , Toast.LENGTH_SHORT).show();
            userFragment.addUserToRecyclerView(sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroupUsernames());
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "error adding!" , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void addJob(String name) {
        sqlDatabaseManager.getGroupJobMappingDatabaseManager().addJobByName(name);
        jobsFragment.addJobToRecyclerView(sqlDatabaseManager.getGroupJobMappingDatabaseManager().getJobsOfCurrentGroup());

    }
}
