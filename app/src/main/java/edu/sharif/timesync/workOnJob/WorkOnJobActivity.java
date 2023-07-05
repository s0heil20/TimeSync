package edu.sharif.timesync.workOnJob;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import edu.sharif.timesync.R;

public class WorkOnJobActivity extends AppCompatActivity {

    int manualHour, manualMinute;
    Chronometer chronometer;
    Button startButton;
    Button resetButton;
    Button setManuallyButton;

    TextView manualTextView;
    Switch chooseSwitch;
    long pauseOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_on_job);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        startButton = (Button) findViewById(R.id.startButton);
        resetButton = (Button) findViewById(R.id.resetButton);


        manualTextView = (TextView) findViewById(R.id.manualTextView);
        setManuallyButton = (Button) findViewById(R.id.setManuallyButton);
        setManuallyButton.setEnabled(false);
        manualTextView.setEnabled(false);


        setManuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });


        chronometer.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d", 0, 0, 0));
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time % 60000)/1000;
                cArg.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d", h, m, s));
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            boolean isWorking = false;

            @Override
            public void onClick(View view) {
                if (!isWorking) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    isWorking = true;
                    startButton.setText("Stop");
                    resetButton.setVisibility(View.INVISIBLE);
                } else {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isWorking = false;
                    startButton.setText("Start");
                    resetButton.setVisibility(View.VISIBLE);
                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                resetButton.setVisibility(View.INVISIBLE);
            }
        });

        chooseSwitch = (Switch) findViewById(R.id.chooseSwitch);

        chooseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chronometer.setEnabled(false);
                    startButton.setEnabled(false);
                    resetButton.setEnabled(false);

                    setManuallyButton.setEnabled(true);
                    manualTextView.setEnabled(true);
                } else{
                    chronometer.setEnabled(true);
                    startButton.setEnabled(true);
                    resetButton.setEnabled(true);

                    setManuallyButton.setEnabled(false);
                    manualTextView.setEnabled(false);
                }

            }
        });
    }


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                manualHour = selectedHour;
                manualMinute = selectedMinute;

                manualTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", manualHour, manualMinute));
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, manualHour, manualMinute, true);
        timePickerDialog.setTitle("Select working time!");
        timePickerDialog.show();

    }

}
