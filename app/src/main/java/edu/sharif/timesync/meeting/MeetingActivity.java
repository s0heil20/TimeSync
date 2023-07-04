package edu.sharif.timesync.meeting;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.sharif.timesync.R;
import edu.sharif.timesync.entity.MeetingChoice;

public class MeetingActivity extends AppCompatActivity {


    private ArrayList<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);


        ArrayList<MeetingChoice> candidateChoices = new ArrayList<>();
        candidateChoices.add(new MeetingChoice(4, "Saturday"));
        candidateChoices.add(new MeetingChoice(7, "Friday"));
        ArrayList<MeetingChoice> selectedChoices = new ArrayList<>();

        Resources r = getResources();
        String name = getPackageName();


        for (String day : MeetingChoice.days) {
            for (int i = 1; i <= 7; i++) {
                int textId = r.getIdentifier(day + i, "id", name);
                TextView textView = (TextView) findViewById(textId);

                MeetingChoice meetingChoice = new MeetingChoice(i, day);
                if (!candidateChoices.contains(meetingChoice)) {
                    textView.setBackgroundColor(Color.parseColor("#bdbdbd"));
                } else {
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!selectedChoices.contains(meetingChoice)) {
                                textView.setBackgroundColor(Color.parseColor("#50C878"));
                                selectedChoices.add(meetingChoice);
                            } else {
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                selectedChoices.remove(meetingChoice);
                            }
                        }
                    });
                }

            }
        }

    }
}
