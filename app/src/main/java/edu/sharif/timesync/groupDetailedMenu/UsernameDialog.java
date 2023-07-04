package edu.sharif.timesync.groupDetailedMenu;

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
import edu.sharif.timesync.groupListMenu.GroupNameDialog;

public class UsernameDialog extends DialogFragment {
    private EditText usernameEditText;

    private UsernameDialog.UsernameDialogListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_username, null);
        builder.setView(view).setTitle("Add User")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = usernameEditText.getText().toString();
                        listener.addUser(username);
                    }
                });
        usernameEditText = view.findViewById(R.id.usernameEditText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (UsernameDialog.UsernameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement UsernameDialogListener!");
        }

    }

    public interface UsernameDialogListener {
        void addUser(String username);
    }
}
