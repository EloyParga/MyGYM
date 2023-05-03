package com.example.appgym.BD;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BBDD_Helper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GYM.db";

    //Constructor
    public BBDD_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Crear BD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_USER);
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_RUTINA);
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_DIA);
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_EJERCICIO);



    }

    //Actualizar BD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_USER);
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_RUTINA);
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_DIA);
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES_EJERCICIO);
        onCreate(db);
    }

    //Cambiar Version BD
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }
}
