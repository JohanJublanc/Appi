package com.example.android.myapplication;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.R.attr.button;

/**
 * Created by Johan on 28/06/2017.
 */

public class ProgramActivity extends AppCompatActivity{

    // objet pour enregistrer
    //le titre du programme
    String nomProgramme;

    // objets pour sélectionner et afficher
    //les matières du programme
    EditText nouvelleMatiere;
    TextView matieresChoisies;
    String[] matieres;
    Button ajoutMatiereFragment ;

    // objets pour sélectionner, stocker et afficher
    //le début et la fin du programme
    EditText affichageDateDebut ;
    EditText affichageDateFin ;
    int date_Debut ;
    int date_Fin ;

    // objets pour sélectionner, enregistrer et afficher
    //les séances d'une semaine type
    NumberPicker numberPickerJour ;
    NumberPicker numberPickerHeures ;
    NumberPicker numberPickerMinutes ;

    TextView affichageProgJours ;
    TextView affichageProgHoraires ;
    TextView affichageTitreProgramme ;

    String[] progJours;
    int[] progHeures ;
    int[] progMinutes ;

    String[] joursSemaine ;


    // Assistant de gestion de la base de données SQLite
    DataBaseHelper MyDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

    //objets pour
    //le titre
        affichageTitreProgramme = (TextView) findViewById(R.id.affichageTitreProgramme) ;

    //objets pour
    //les date de début et de fin

        affichageDateDebut = (EditText) findViewById(R.id.affichageDateDebut) ;
        affichageDateFin = (EditText) findViewById(R.id.affichageDateFin) ;

    //objets pour
    //les matière
        matieresChoisies = (TextView) findViewById(R.id.matieresChoisies) ;
        nouvelleMatiere = (EditText) findViewById(R.id.nouvelleMatiere) ;
        nouvelleMatiere.setHint("Nouvelle matière") ;
        matieres = new String[0] ;
        ajoutMatiereFragment = (Button) findViewById(R.id.ajoutMatiereFragment);
        ajoutMatiereFragment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                enregistrerMatieres();
            }
        }

        );

    //objets pour
    //les séances d'une semaine type
        progJours = new String[0] ;
        progHeures = new int[0] ;
        progMinutes = new int[0] ;

        affichageProgJours = (TextView) findViewById(R.id.affichageProgJours);
        affichageProgHoraires = (TextView) findViewById(R.id.affichageProgHoraires);

        numberPickerJour = (NumberPicker) findViewById(R.id.numberPickerJour) ;
        numberPickerJour.setMinValue(1);
        numberPickerJour.setMaxValue(7);
        joursSemaine = new String[]{"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"} ;
        numberPickerJour.setDisplayedValues(joursSemaine);

        numberPickerHeures = (NumberPicker) findViewById(R.id.numberPickerHeures) ;
        numberPickerHeures.setMinValue(0);
        numberPickerHeures.setMaxValue(24);

        numberPickerMinutes = (NumberPicker) findViewById(R.id.numberPickerMinutes) ;
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);

    //Gestionnaire de base de données
        MyDB = new DataBaseHelper(this) ;
    }

    public void ajouterSeance (View view){
        ajouterJour();
        ajouterHoraires();

    }

    public void ajouterJour (){
        final StringBuffer stringBuffer = new StringBuffer();
        final String[] progJoursPlus = new String[progJours.length + 1];

        for(int i=0;i<progJours.length;i++){
            progJoursPlus[i]=progJours[i];
            stringBuffer.append(progJours[i] + "\n") ;
        }

        progJoursPlus[progJours.length]=joursSemaine[numberPickerJour.getValue()-1];
        stringBuffer.append(progJoursPlus[progJours.length]);

        affichageProgJours.setText(""+ stringBuffer) ;
        progJours=progJoursPlus ;
    }

    public void ajouterHoraires (){
        final StringBuffer stringBuffer = new StringBuffer();
        final int[] progHeuresPlus = new int[progHeures.length + 1];
        final int[] progMinutesPlus = new int[progMinutes.length + 1];

        for(int i=0;i<progHeures.length;i++){
            progHeuresPlus[i]=progHeures[i];
            progMinutesPlus[i]=progMinutes[i] ;
            stringBuffer.append(""+ progHeures[i] + "H " + progMinutes[i] + "\n") ;
        }

        progHeuresPlus[progHeures.length]=(numberPickerHeures.getValue()) ;
        progMinutesPlus[progMinutes.length]=(numberPickerMinutes.getValue()) ;
        stringBuffer.append("" + progHeuresPlus[progHeures.length]+"H "+ progMinutesPlus[progMinutes.length]);

        affichageProgHoraires.setText(""+ stringBuffer) ;
        progHeures=progHeuresPlus ;
        progMinutes=progMinutesPlus ;
    }

    public void validerProgramme(View view){

        for(int i=0; i<progJours.length ; i++){
            MyDB.insertProgramme(nomProgramme, date_Debut, date_Fin,progJours[i],progHeures[i],progMinutes[i]);
        }
    }

    public void enregitrerDateDebut (View view){
        DatePickerFragment datePickerFragment = new DatePickerFragment(affichageDateDebut);
        datePickerFragment.show(getSupportFragmentManager(),"Choix date de début");
    }

    public void enregitrerDateFin (View view){
        DatePickerFragment datePickerFragment = new DatePickerFragment(affichageDateFin);
        datePickerFragment.show(getSupportFragmentManager(),"Choix date de fin");
    }


    public void enregistrerTitre(View view) {
        DialogFragment newFragment = TitrePickerFragment.newInstance(R.string.alert_dialog_titreProgramme_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

            public void doPositiveClick(EditText edittext) {
                affichageTitreProgramme.setText(edittext.getText()) ;
                Log.i("FragmentAlertDialog", "Positive click!");
            }

            public void doNegativeClick() {
                // Do stuff here.
                Log.i("FragmentAlertDialog", "Negative click!");
            }
    public void enregistrerMatieres() {
        DialogFragment choixMatieresFragment = MatieresPickerFragment.newInstance(R.string.alert_dialog_Matieres_title);

        choixMatieresFragment.show(getFragmentManager(), "dialog");
        /*plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final StringBuffer stringBuffer = new StringBuffer();
                final String[] matieresPlus = new String[matieres.length + 1];

                for (int i = 0; i < matieres.length; i++) {
                    matieresPlus[i] = matieres[i];
                    stringBuffer.append(matieres[i] + "\n");
                }

                matieresPlus[matieres.length] = nouvelleMatiere.getText().toString();
                stringBuffer.append(matieresPlus[matieres.length]);

                matieresChoisies.setText("" + stringBuffer);
                matieres = matieresPlus;
                nouvelleMatiere.setText("");
            }
        });*/
    }

    public void doPositiveClickMatieres(String[] matieres) {

                Log.i("FragmentAlertDialog", "Positive click!");
            }

            public void doNegativeClickMatieres() {
                // Do stuff here.
                Log.i("FragmentAlertDialog", "Negative click!");
            }



}
