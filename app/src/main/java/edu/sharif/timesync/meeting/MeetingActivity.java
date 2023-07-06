package edu.sharif.timesync.meeting;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.MeetingCandidateTimeDatabaseManager;
import edu.sharif.timesync.database.MeetingChoiceDatabaseManager;
import edu.sharif.timesync.database.MeetingDatabaseManager;
import edu.sharif.timesync.database.SQLDatabaseManager;
import edu.sharif.timesync.database.UserDatabaseManager;
import edu.sharif.timesync.entity.Meeting;
import edu.sharif.timesync.entity.MeetingChoice;

public class MeetingActivity extends AppCompatActivity {


    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(this);
        MeetingCandidateTimeDatabaseManager meetingCandidateTimeDatabaseManager =
                MeetingCandidateTimeDatabaseManager.instanceOfMeetingCandidateDatabaseManager(sqlDatabaseManager);
        MeetingChoiceDatabaseManager meetingChoiceDatabaseManager =
                MeetingChoiceDatabaseManager.instanceOfMeetingChoiceDatabaseManager(sqlDatabaseManager);
        UserDatabaseManager userDatabaseManager = UserDatabaseManager.instanceOfUserDatabaseManager(sqlDatabaseManager);


        Bundle extras = getIntent().getExtras();
        String meetingName;
        boolean isLeader;
        if (extras != null) {
            meetingName = extras.getString("name");
            isLeader = extras.getBoolean("isLeader");

            boolean isFinalized = meetingChoiceDatabaseManager.isMeetingFinalized(meetingName);

            ArrayList<MeetingChoice> clickableChoices;
            ArrayList<MeetingChoice> selectedChoices = new ArrayList<>();

            int defaultColor;
            int clickableColor;
            int selectedColor;

            if (isFinalized) {
                clickableChoices = meetingChoiceDatabaseManager.getAcceptedMeetingChoice(meetingName);
                defaultColor = Color.parseColor("#bdbdbd");
                clickableColor = Color.parseColor("#50C878");
                selectedColor = Color.parseColor("#50C878");
            } else {
                if (isLeader) {
                    clickableChoices = new ArrayList<>();
                    for (int i = 0; i < 49; i++) {
                        clickableChoices.add(MeetingChoice.getMeetingChoiceFromInt(i));
                    }
                    defaultColor = Color.parseColor("#FFFFFF");
                    clickableColor = Color.parseColor("#FFFFFF");
                    selectedColor = Color.parseColor("#50C878");

                } else {
                    clickableChoices = meetingCandidateTimeDatabaseManager.getMeetingCandidateTimes(meetingName);
                    defaultColor = Color.parseColor("#bdbdbd");
                    clickableColor = Color.parseColor("#FFFFFF");
                    selectedColor = Color.parseColor("#50C878");
                }
            }


            Resources r = getResources();
            String name = getPackageName();


            for (String day : MeetingChoice.days) {
                for (int i = 1; i <= 7; i++) {
                    int textId = r.getIdentifier(day + i, "id", name);
                    TextView textView = (TextView) findViewById(textId);
                    textView.setText("");

                    MeetingChoice meetingChoice = new MeetingChoice(i, day);
                    if (!clickableChoices.contains(meetingChoice)) {
                        textView.setBackgroundColor(defaultColor);
                    } else {
                        textView.setBackgroundColor(clickableColor);
                        if (!isFinalized) {
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (!selectedChoices.contains(meetingChoice)) {
                                        textView.setBackgroundColor(selectedColor);
                                        selectedChoices.add(meetingChoice);
                                    } else {
                                        textView.setBackgroundColor(clickableColor);
                                        selectedChoices.remove(meetingChoice);
                                    }
                                }
                            });
                        }
                    }

                }
            }

            submitButton = (Button) findViewById(R.id.submitMeetingButton);
            MeetingDatabaseManager meetingDatabaseManager = MeetingDatabaseManager.instanceOfMeetingDatabaseManager(sqlDatabaseManager);

            submitButton.setOnClickListener(new View.OnClickListener() {
                final String loggedInUsername = userDatabaseManager.getLoggedInUser().getUsername();

                @Override
                public void onClick(View view) {
                    if (isLeader) {
                        meetingCandidateTimeDatabaseManager.addAllCandidateTime(meetingName, selectedChoices);

                    } else {
                        meetingChoiceDatabaseManager.addAllChoiceTimes(meetingName, loggedInUsername, selectedChoices);
                    }
                    meetingDatabaseManager.finalizeMeetingIfFinalized(meetingName);
                    finish();
                }
            });
        }

    }

}

