package com.example.android.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog ;
import android.app.DialogFragment;
import android.widget.EditText;

import static android.R.id.edit;

/**
 * Created by Johan on 01/07/2017.
 */

public class TitrePickerFragment extends DialogFragment {

    public static TitrePickerFragment newInstance(int title) {
        TitrePickerFragment frag = new TitrePickerFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        final EditText edittext = new EditText(this.getContext());

        return new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.alert_dialog_icon)
                .setTitle(title)
                .setView(edittext)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((ProgramActivity)getActivity()).doPositiveClick(edittext);
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((ProgramActivity)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }

}