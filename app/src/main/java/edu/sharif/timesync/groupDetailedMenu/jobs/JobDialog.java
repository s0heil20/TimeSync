package edu.sharif.timesync.groupDetailedMenu.jobs;

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
import edu.sharif.timesync.groupDetailedMenu.user.UsernameDialog;

public class JobDialog extends DialogFragment {

    private EditText jobNameEditText;

    private JobDialog.jobDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_job, null);
        builder.setView(view).setTitle("Add Job")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String jobName = jobNameEditText.getText().toString();
                        listener.addJob(jobName);
                    }
                });
        jobNameEditText = view.findViewById(R.id.jobEditText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (JobDialog.jobDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement JobDialog.jobDialogListener!");
        }

    }

    public interface jobDialogListener {
        void addJob(String name);
    }
}
