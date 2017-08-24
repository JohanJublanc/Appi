package com.example.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Johan on 30/05/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final  String DATABASE_NAME = "AppiDataBase6.db";

    public static final  String TABLE_BILAN = "Seances_table1";
    public static final  String COL_1 = "ID";
    public static final  String COL_2 = "DATE";
    public static final  String COL_3 = "DUREE_MILLIS";
    public static final  String COL_4 = "MATIERE";

    public static final  String TABLE_PROGRAMMES = "Programmes_table1";
    public static final  String P_COL_1 = "ID";
    public static final  String P_COL_2 = "NomProg";
    public static final  String P_COL_3 = "Date_Debut";
    public static final  String P_COL_4 = "Date_Fin";
    public static final  String P_COL_5 = "Jour";
    public static final  String P_COL_6 = "Heure";
    public static final  String P_COL_7 = "Minutes";

    public static final  String TABLE_MATIERES = "Matieres_table1";
    public static final  String M_COL_1 = "ID";
    public static final  String M_COL_2 = "NomProg";
    public static final  String M_COL_3 = "Matieres";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BILAN + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        "DATE INTEGER," +
                                                        "DUREE_MILLIS INTEGER," +
                                                        " MATIERE TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_PROGRAMMES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        " NomProg TEXT," +
                                                        " Date_Debut INTEGER," +
                                                        " Date_Fin INTEGER," +
                                                        " Jour TEXT)," +
                                                        "Heure INTEGER,"+
                                                        "Minutes INTEGER");

        db.execSQL("CREATE TABLE " + TABLE_MATIERES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        " NomProg TEXT," +
                                                        " Matieres TEXT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BILAN);
    }

    public boolean insertData(long date,long duree_millis, String matiere){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,duree_millis);

        contentValues.put(COL_4,matiere);
        long result = db.insert(TABLE_BILAN,null,contentValues);
        db.close();

        //To Check Whether Data is Inserted in DataBase
        return result != -1;
    }

    public boolean insertProgramme(String NomProg,int Date_Debut, int Date_Fin, String Jour,int Heure, int Minutes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(P_COL_2, NomProg);
        contentValues.put(P_COL_3, Date_Debut);
        contentValues.put(P_COL_4, Date_Fin);
        contentValues.put(P_COL_5, Jour);
        contentValues.put(P_COL_6, Heure);
        contentValues.put(P_COL_7, Minutes);

        long result = db.insert(TABLE_PROGRAMMES, null, contentValues);
        db.close();

        return result != -1;
    }

    public boolean insertMatieres(String NomProg,String matieres) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(M_COL_2, NomProg);
        contentValues.put(M_COL_3, matieres);

        long result = db.insert(TABLE_MATIERES, null, contentValues);
        db.close();

        return result != -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_BILAN,null);
        return  res;
    }
}

