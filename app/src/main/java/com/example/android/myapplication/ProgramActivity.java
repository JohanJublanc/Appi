package com.example.android.myapplication;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import static android.R.attr.button;
import static com.example.android.myapplication.R.id.affichageProgHoraires;

/**
 * Created by Johan on 28/06/2017.
 */

public class ProgramActivity extends AppCompatActivity{
    EditText nouvelleMatiere;
    String[] matieres;
    TextView matieresChoisies;

    NumberPicker numberPickerJour ;
    NumberPicker numberPickerHeures ;
    NumberPicker numberPickerMinutes ;

    TextView affichageProgJours ;
    TextView affichageProgHoraires ;

    String nomProgramme;
    int date_Debut ;
    int date_Fin ;

    String[] progJours;
    int[] progHeures ;
    int[] progMinutes ;
    String[] joursSemaine ;

    DataBaseHelper MyDB ;

    DatePickerFragment datePickerFragment ;

    String date ;
    TextView affichageDateDebut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

    matieresChoisies = (TextView) findViewById(R.id.matieresChoisies) ;
    nouvelleMatiere = (EditText) findViewById(R.id.nouvelleMatiere) ;
    nouvelleMatiere.setHint("Nouvelle matière") ;
    matieres = new String[0] ;
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

    MyDB = new DataBaseHelper(this) ;

    nomProgramme = "Nouveau Programme" ;
    date_Debut = 10 ;
    date_Fin = 10 ;

    datePickerFragment = new DatePickerFragment();

    date = "nouvelle date" ;


    /*datePickerFragment.doPositiveClick(){
            date = datePickerFragment.date();
            affichageDateDebut.setText(date);
        }
    */

    affichageDateDebut = (TextView) findViewById(R.id.affichageDateDebut) ;
    affichageDateDebut.setText(date);

    }
    public void ajouterMatiere(View view){
        final StringBuffer stringBuffer = new StringBuffer();
        final String[] matieresPlus = new String[matieres.length +1]  ;

        for(int i=0;i<matieres.length;i++){
            matieresPlus[i]=matieres[i];
            stringBuffer.append(matieres[i] + "\n") ;
        }

        matieresPlus[matieres.length] = nouvelleMatiere.getText().toString();
        stringBuffer.append(matieresPlus[matieres.length]);

        matieresChoisies.setText(""+stringBuffer) ;
        matieres = matieresPlus ;
        nouvelleMatiere.setText("");
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
        datePickerFragment.show(getSupportFragmentManager(),"Test");
    }
}
