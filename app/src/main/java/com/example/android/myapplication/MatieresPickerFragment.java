package com.example.android.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Johan on 01/07/2017.
 */

public class MatieresPickerFragment extends DialogFragment {

    StringBuffer stringBuffer ;
    String[] matieres;
    EditText test ;
    TextView affichageMatieres ;
    EditText nouvelleMatiere ;
    Button addMatiere ;

    public static MatieresPickerFragment newInstance(int title) {
        MatieresPickerFragment frag = new MatieresPickerFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        int title = getArguments().getInt("title");

        //constructeur de l'AlerteDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate the view
        final View customView = inflater.inflate(R.layout.fragment_matierespicker, null);

        final TextView textMatieres = (TextView) customView.findViewById(R.id.affichageMatieres);
        final EditText editMatiere = (EditText) customView.findViewById(R.id.nouvelleMatiere);
        final Button addMatiere = (Button) customView.findViewById(R.id.addMatiere);
        matieres = new String[0];
        addMatiere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                stringBuffer  = new StringBuffer();
                final String[] matieresPlus = new String[matieres.length + 1];
                for (int i = 0; i < matieres.length; i++) {
                    matieresPlus[i] = matieres[i];
                    stringBuffer.append(matieres[i] + "\n");
                }
                matieresPlus[matieres.length] = editMatiere.getText().toString();
                stringBuffer.append(matieresPlus[matieres.length]);
                textMatieres.setText("" + stringBuffer);
                matieres = matieresPlus;
                editMatiere.setText("");
            }
        });

        builder.setView(customView);
        builder.setTitle(title)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((ProgramActivity) getActivity()).doPositiveClickMatieres(matieres);
                            }
                        })

                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((ProgramActivity) getActivity()).doNegativeClickMatieres();

                            }
                        });
        return builder.create();
    }
}

