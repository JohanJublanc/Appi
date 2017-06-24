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
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Calendar;

/**
 * Created by Johan on 05/06/2017.
 */

public class ChoixSeanceActivity extends AppCompatActivity {
    NumberPicker numberPickerHours;
    NumberPicker numberPickerMinutes;
    Button playPause;
    Button fin;
    TextView minuteurAffichage ;
    private static final String FORMAT = "%02d:%02d:%02d";
    CountDownTimer minuteur ;
    DataBaseHelper MyDB ;
    Spinner spinner ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choixseance);

        playPause = (Button) findViewById(R.id.playPause) ;
        fin = (Button) findViewById(R.id.fin) ;

        numberPickerHours = (NumberPicker) findViewById(R.id.numberPickerHours2);
        numberPickerHours.setMaxValue(24);
        numberPickerHours.setMinValue(0);

        numberPickerMinutes = (NumberPicker) findViewById(R.id.numberPickerMinutes2);
        numberPickerMinutes.setMaxValue(60);
        numberPickerMinutes.setMinValue(0);

        minuteurAffichage = (TextView) findViewById(R.id.minuteur) ;

        MyDB = new DataBaseHelper(this);

        spinner = (Spinner) findViewById(R.id.matieres) ;

        String[] matieres ={"Maths","Français"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,matieres);
        spinner.setAdapter(adapter);

        // Création d'un ArrayAdapter utilisant le stringArray dans les ressources textuelles et un spinner par défaut
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Matieres,android.R.layout.simple_spinner_dropdown_item);
        // layout à utiliser lorque la liste apparaît
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applique l'adapter au spinner
        // spinnerMatieres.setAdapter(adapter);

    }
    public void playPause (View view) {

        final long date = System.currentTimeMillis();
        final int Heures = numberPickerHours.getValue();
        final int Minutes = numberPickerMinutes.getValue();
        final long dureeMillis = (Heures * 3600000 + Minutes * 60000) ;
        final String matiere = spinner.getSelectedItem().toString(); ;

        playPause.setClickable(FALSE);
        minuteurAffichage.setVisibility(View.VISIBLE);
        //picker.setVisibility(LinearLayout.GONE);


        minuteur = new CountDownTimer(dureeMillis, 1000) {
            int i = 0 ;
            public void onTick(final long millisUntilFinished) {
                minuteurAffichage.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            i=i+1;
            }
            public void onFinish() {
                minuteurAffichage.setText("Bien joué ma gueule !");
                playPause.setClickable(TRUE);
                int heuresSeance = (i/3600) ;
                int minutesSeance = (i/60) - heuresSeance*60 ;
                Boolean results = MyDB.insertData(date, dureeMillis,matiere);
                /*if(results==false) {
                    Toast.makeText(this, "Aucune donnée enregistrée", Toast.LENGTH_SHORT).show();
                };*/
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
            playPause.setClickable(TRUE);
            minuteurAffichage.setText(""+ String.format(FORMAT,0,0,0));
        }

}
