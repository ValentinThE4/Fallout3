package com.example.utilisateur.fallout3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {

    public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatPers = "create table personnages (id int, prenom text, nom text, age int, role int, nomPhoto string, idLieu, idRace)";
        String creatRace = "create table races (id int, libelle text, descriptif text)";
        String creatLieu = "create table lieux (id int, libelle text, descriptif text)";

        db.execSQL(creatLieu);
        db.execSQL(creatRace);
        db.execSQL(creatPers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
