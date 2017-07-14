package com.example.android.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.onClick;
import static com.example.android.myapplication.R.id.affichageMatieres;
import static com.example.android.myapplication.R.id.matieres;
import static com.example.android.myapplication.R.id.matieresChoisies;
import static com.example.android.myapplication.R.id.nouvelleMatiere;

/**
 * Created by Johan on 01/07/2017.
 */

public class MatieresPickerFragment extends DialogFragment {

    String[] matieres ;
    EditText test ;
    TextView affichageMatieres ;
    EditText nouvelleMatiere ;
    Button plus ;

    public static MatieresPickerFragment newInstance(int title) {
        MatieresPickerFragment frag = new MatieresPickerFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    // Test pour customiser le layout cf. https://stackoverflow.com/questions/15990995/android-findviewbyid-in-dialogfragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // R.layout.my_layout - that's the layout where your textview is placed
        final View view = inflater.inflate(R.layout.fragment_choixmatieres, container, false);
        affichageMatieres = (TextView) view.findViewById(R.id.affichageMatieres);
        nouvelleMatiere = (EditText) view.findViewById(R.id.nouvelleMatiere);
        //affichageMatieres = (TextView) view.findViewById(R.id.affichageMatieres);

        ((TextView) view.findViewById(R.id.affichageMatieres)).setText("Pouet");
        plus = (Button) view.findViewById(R.id.addMatiere) ;
        plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
            affichageMatieres.setText("Nouvelle matière");
            }
        });
        // you can use your textview.
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        int title = getArguments().getInt("title");

        //final EditText nouvelleMatiere = new EditText(this.getContext());

        matieres = new String[0];


         //affichageMatieres = new TextView(this.getContext());
         //       affichageMatieres.setText("Test" +"\n"+"Test" +"\n"+"Test" +"\n");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_choixmatieres, null))
                // Add action buttons
                //.setIcon(R.drawable.alert_dialog_icon)
                .setTitle(title)
                .setNeutralButton(R.string.ajout,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                {
                                    test = (EditText) getView().findViewById(R.id.testMatiere);
                                    affichageMatieres.setText("Victory");

    /*                               final StringBuffer stringBuffer = new StringBuffer();
                                    final String[] matieresPlus = new String[matieres.length + 1];


                                    for (int i = 0; i < matieres.length; i++) {
                                        matieresPlus[i] = matieres[i];
                                        stringBuffer.append(matieres[i] + "\n");
                                    }

                                    matieresPlus[matieres.length] = nouvelleMatiere.getText().toString();
                                    stringBuffer.append(matieresPlus[matieres.length]);

                                    affichageMatieres.setText("" + stringBuffer);
                                    matieres = matieresPlus;
                                    nouvelleMatiere.setText("");*/
                                                                   }
                            }
                        })

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

