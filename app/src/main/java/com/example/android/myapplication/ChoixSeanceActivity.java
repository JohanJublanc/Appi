package com.example.android.myapplication;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.R.interpolator.linear;
import static com.example.android.myapplication.DataBaseHelper.COL_2;
import static com.example.android.myapplication.R.id.matieres;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Calendar;

/**
 * Created by Johan on 05/06/2017.
 */

public class ChoixSeanceActivity extends AppCompatActivity {
 // Les Layout
    LinearLayout picker ;
    LinearLayout afficheur ;

 // Les pickers pour choisir la durée de la séance
    NumberPicker numberPickerHours;
    NumberPicker numberPickerMinutes;
    NumberPicker numberPickerSecondes;

//  Les bouttons
    Button play;
    Button pause;
    Button fin;
    Button stop;

// La vue d'affichage du compte à rebours
    TextView minuteurAffichage ;

// Le spinner permettant de sélectionner la matière
    Spinner spinner ;

// Format pour l'affichage du compte à rebours
    private static final String FORMAT = "%02d:%02d:%02d";

// Le compteur à rebour
    CountDownTimer minuteur ;

// L'assistant de gestion de la base de données SQLite où sont enregistrées les séances
    DataBaseHelper MyDB ;

    long dureeRestanteMillis ;
    boolean pausePlay ;

    TextView test ;
            TextView test2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choixseance);

        play = (Button) findViewById(R.id.play) ;
        //play.setBackgroundResource(R.drawable.play);

        pause = (Button) findViewById(R.id.pause) ;
        //pause.setBackgroundResource(R.drawable.pause);

        fin = (Button) findViewById(R.id.fin) ;
        //fin.setBackgroundResource(R.drawable.retour);

        stop = (Button) findViewById(R.id.stop) ;
        //stop.setBackgroundResource(R.drawable.stop);
        stop.setClickable(FALSE);

        numberPickerHours = (NumberPicker) findViewById(R.id.numberPickerHours2);
        numberPickerHours.setMaxValue(24);
        numberPickerHours.setMinValue(0);

        numberPickerMinutes = (NumberPicker) findViewById(R.id.numberPickerMinutes2);
        numberPickerMinutes.setMaxValue(60);
        numberPickerMinutes.setMinValue(0);

        numberPickerSecondes = (NumberPicker) findViewById(R.id.numberPickerSecondes2);
        numberPickerSecondes.setMaxValue(60);
        numberPickerSecondes.setMinValue(0);

        minuteurAffichage = (TextView) findViewById(R.id.minuteur) ;
        picker = (LinearLayout) findViewById(R.id.picker) ;
        afficheur = (LinearLayout) findViewById(R.id.afficheur) ;
        afficheur.setVisibility(View.GONE);

        MyDB = new DataBaseHelper(this);

        spinner = (Spinner) findViewById(matieres) ;

        // Création d'un ArrayAdapter utilisant le stringArray dans les ressources textuelles et un spinner par défaut
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Matieres,android.R.layout.simple_spinner_dropdown_item);
        // layout à utiliser lorque la liste apparaît
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //String[] matieres ={"Maths","Français"};
       // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,matieres);

        // Applique l'adapter au spinner
        spinner.setAdapter(adapter);


        dureeRestanteMillis = (long) 0;
        pausePlay = FALSE ;

        test= (TextView) findViewById(R.id.test) ;
        test2= (TextView) findViewById(R.id.test2) ;


    }


    public void play (View view) {
// Récupération des informations relatives à la séance
        final long date = System.currentTimeMillis();
        final int Heures = numberPickerHours.getValue();
        final int Minutes = numberPickerMinutes.getValue();
        final int Secondes = numberPickerSecondes.getValue();
        final String matiere = spinner.getSelectedItem().toString();

// Récupération de la durée de la séance
        final long dureeMillis = calculDuree(pausePlay,Heures,Minutes,dureeRestanteMillis) ;

// Désactivation de play et du spinner et activation de la touche stop
        play.setClickable(FALSE);
        spinner.setClickable(FALSE);
        stop.setClickable(TRUE);

//Passage de l'affichage des pickers à l'affichage du compte à rebour
        picker.setVisibility(View.GONE);
        afficheur.setVisibility(View.VISIBLE);

// Lancement du minuteur
        minuteur = new CountDownTimer(dureeMillis, 1000) {

            public void onTick(final long millisUntilFinished) {
                minuteurAffichage.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            dureeRestanteMillis = millisUntilFinished ;
            }

            public void onFinish() {

                //Enregistrement des données dans la base SQLite
                MyDB.insertData(date, Heures * 3600000 + Minutes * 60000 - dureeRestanteMillis + 10000, matiere);
                test.setText("OK OK " + (Heures * 3600000 + Minutes * 60000)) ;
                test2.setText("" + dureeRestanteMillis);
                //Indicateur de pause remis à False et réinitialisation de la durée restante
                pausePlay = FALSE ;
                dureeRestanteMillis = 0 ;

                //Réactivation de play et du spinner, désactivation de stop
                play.setClickable(TRUE);
                spinner.setClickable(TRUE);
                stop.setClickable(FALSE);

                //Affichage des pickers et disparition de l'afficheur de compte à rebours
                picker.setVisibility(View.VISIBLE);
                afficheur.setVisibility(View.GONE);

            }

        }.start();
    }

        public void finSeance (View v){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        public void stopSeance (View v){
            minuteur.onFinish();
            minuteur.cancel();
            play.setClickable(TRUE);
            minuteurAffichage.setText(""+ String.format(FORMAT,0,0,0));
        }

        public void pause (View v){
            minuteur.cancel();
            pausePlay = TRUE ;
            play.setClickable(TRUE);
        }

    public long calculDuree(boolean pausePlay,int Heures,int Minutes,long dureeRestanteMillis){
        long dureeMillis ;
        if (pausePlay==FALSE){
            dureeMillis =(Heures * 3600000 + Minutes * 60000) ;
        }else{ dureeMillis = dureeRestanteMillis ;
        }
        return dureeMillis ;
    }


}
