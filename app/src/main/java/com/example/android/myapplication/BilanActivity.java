package com.example.android.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.LongBuffer;
import java.util.Date;

/**
 * Created by Johan on 05/06/2017.
 */

public class BilanActivity extends AppCompatActivity

    {

    DataBaseHelper MyDB ;
    TextView bilanId ;
    TextView bilanHeures;
        TextView bilanMinutes;
        TextView bilanDates ;
TextView bilanMatieres ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);
        MyDB = new DataBaseHelper(this) ;

        bilanId = (TextView) findViewById(R.id.bilanId);
        bilanHeures = (TextView) findViewById(R.id.bilanHeures);
        bilanMinutes = (TextView) findViewById(R.id.bilanMinutes);
        bilanDates = (TextView) findViewById(R.id.bilanDates) ;
        bilanMatieres = (TextView) findViewById(R.id.bilanMatieres);
    }
    public void fermerBilan (View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void afficherMyDB(View view){
        Cursor res = MyDB.getAllData();

        StringBuffer stringBuffer = new StringBuffer();


        if(res!=null && res.getCount()>0){
            while (res.moveToNext()){
                stringBuffer.append(" "+res.getString(0)+"\n");}
                bilanId.setText(stringBuffer.toString());
                res.moveToPosition(-1);
                stringBuffer.delete(0,stringBuffer.length());
            }

        if(res!=null && res.getCount()>0){
            while (res.moveToNext()){
                Long Heures ;
                Heures = res.getLong(2)/3600000 ;
                stringBuffer.append(Heures+" H " +"\n");
            }
            bilanHeures.setText(""+stringBuffer.toString());
            res.moveToPosition(-1);
            stringBuffer.delete(0,stringBuffer.length());
        }
        //

        if(res!=null && res.getCount()>0){
            while (res.moveToNext()){
                Long Minutes ;
                Long Heures ;
                Heures = res.getLong(2)/3600000 ;
                Minutes = res.getLong(2)/60000 - Heures*60 ;
                stringBuffer.append(Minutes +" Min" +"\n");
            }
            bilanMinutes.setText(" "+stringBuffer.toString());
            res.moveToPosition(-1);
            stringBuffer.delete(0,stringBuffer.length());
        }

     if(res!=null && res.getCount()>0){
            while (res.moveToNext()){
                stringBuffer.append(" "+ DateFormat.format("dd/MM/yyyy", new Date(res.getLong(1))).toString()+"\n");}
            bilanDates.setText(stringBuffer.toString());
            res.moveToPosition(-1);
            stringBuffer.delete(0,stringBuffer.length());
        }

        if(res!=null && res.getCount()>0){
            while (res.moveToNext()){
                stringBuffer.append(" "+res.getString(3)+"\n");}
            bilanMatieres.setText(stringBuffer.toString());
            res.moveToPosition(-1);
            stringBuffer.delete(0,stringBuffer.length());
        }

    }

        public void ajoutEnregistrement (View view){
            Boolean results=MyDB.insertData(10,40000000,"matière");
            if(results==false) {
                Toast.makeText(this, "Aucune donnée enregistrée", Toast.LENGTH_SHORT).show();
            }
        }
       /* public void mettreAJoursMyDB(View view) {
        MyDB.updateData("id","test", 0, 20);
        }
        */

}
