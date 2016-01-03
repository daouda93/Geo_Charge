package com.example.melik.geocharge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Melik on 28/12/2015.
 */
public class Database extends SQLiteOpenHelper {

    //colonne markker
    public static final String MARKER_TYPE="type";
    public static final String MARKER_DETAILS="details";
    public static final String MARKER_LAT="lat";
    public static final String MARKER_LONG="long";
    public static final String MARKER_NAME="nom";

    //marker table name
    public static final String TABLE_NAME="Markers";

    //db name
    public static final String DATABASE_NAME= "Geocharge.db";

    //database version
    public static final int DATABASE_VERSION=1;

    //operations
    public static final String MARKER_TABLE_CREATE=
            "CREATE TABLE "+TABLE_NAME+" (" +
                    MARKER_TYPE + " TEXT, " +
                    MARKER_DETAILS + " TEXT, " +
                    MARKER_LAT + " REAL," +
                    MARKER_LONG + " REAL," +
                    MARKER_NAME + " TEXT);";
    public static final String MARKKER_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MARKER_TABLE_CREATE);
    } // on crée la table

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // quand on actualise la base ça la suprimme et récrée
        db.execSQL(MARKKER_TABLE_DROP);
        onCreate(db);
    }

    public void ajouterBorne(Borne borne){ // méthode pour ajouté une borne a la bdd sous forme d'un tuple
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(MARKER_TYPE, borne.getType());
        value.put(MARKER_DETAILS, borne.getDetails());
        value.put(MARKER_LAT, borne.getLatitude());
        value.put(MARKER_LONG, borne.getLongitude());
        value.put(MARKER_NAME, borne.getNom());

        db.insert(TABLE_NAME, null, value);
        db.close();
    }

    public void supprimerBorne(Borne borne){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, MARKER_LAT +" = ? AND "+MARKER_LONG+" = ?",new String[]{String.valueOf(borne.getLatitude()),String.valueOf(borne.getLongitude())});
    }

    public ArrayList<Borne> getAllBorne(){ //methodes pour récup toute les bornnes de la BDD et les retourne sous forme d'arraylist
        ArrayList<Borne> bornesliste=new ArrayList<>();
        String select ="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do {
                String type=cursor.getString(0);
                String details=cursor.getString(1);
                double latitude=cursor.getDouble(2);
                double longitude=cursor.getDouble(3);
                Borne borne=new Borne(type,details,latitude,longitude);
                bornesliste.add(borne);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return bornesliste;
    }


}