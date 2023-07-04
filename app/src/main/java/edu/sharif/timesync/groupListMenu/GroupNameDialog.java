package edu.sharif.timesync.groupListMenu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import edu.sharif.timesync.R;

public class GroupNameDialog extends DialogFragment {

    private EditText groupNameEditText;

    private GroupDialogListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_group_name, null);
        builder.setView(view).setTitle("Add Group")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String groupName = groupNameEditText.getText().toString();
                        listener.addGroup(groupName);
                    }
                });
        groupNameEditText = view.findViewById(R.id.groupNameEditText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (GroupDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement GroupDialogListener!");
        }

    }

    public interface GroupDialogListener{
        void addGroup(String groupName);
    }
}
