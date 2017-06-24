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
    public static final  String TABLE_NAME = "Seances_table1";

    public static final  String COL_1 = "ID";
    public static final  String COL_2 = "DATE";
    public static final  String COL_3 = "DUREE_MILLIS";

    public static final  String COL_4 = "MATIERE";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE INTEGER,DUREE_MILLIS INTEGER, MATIERE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(long date,long duree_millis, String matiere){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,duree_millis);

        contentValues.put(COL_4,matiere);
        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();

        //To Check Whether Data is Inserted in DataBase
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
        return  res;
    }
}

