package edu.sharif.timesync.groupDetailedMenu.meeting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import edu.sharif.timesync.R;


public class MeetingDialog extends DialogFragment {
    private EditText meetingNameEditText;

    private MeetingDialog.meetingDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_meeting, null);
        builder.setView(view).setTitle("Add Meeting")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String meetingName = meetingNameEditText.getText().toString();
                        listener.addMeeting(meetingName);
                    }
                });
        meetingNameEditText = view.findViewById(R.id.meetingEditText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (MeetingDialog.meetingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement MeetingDialog.meetingDialogListener!");
        }

    }

    public interface meetingDialogListener {
        void addMeeting(String name);
    }
}
