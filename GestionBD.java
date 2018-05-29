package com.example.utilisateur.fallout3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GestionBD {

    private SQLiteDatabase maBase;
    private BDHelper monBDHelper;

    public GestionBD(Context context){
        monBDHelper = new BDHelper(context, "basePersoFallout", null, 1);
    }

    public void open(){
        maBase = monBDHelper.getWritableDatabase();
    }

    public void close(){
        maBase.close();
    }

    public long ajoutePers(Personnages pers){
        ContentValues v = new ContentValues();
        v.put("id", pers.getId());
        v.put("prenom", pers.getPrenom());
        v.put("nom", pers.getNom());
        v.put("age", pers.getAge());
        v.put("role", pers.getRole());
        v.put("nomPhoto", pers.getNom_photo());
        v.put("idLieu", pers.getId_lieu());
        v.put("idRace", pers.getId_race());
        return maBase.insert("personnages",null, v);
    }

    public void supprimePersonnages(){
        maBase.delete("personnages", null, null);
    }

    public ArrayList<String> donneLesNoms(){
        ArrayList<String> liste = new ArrayList<String>();
        Cursor c = maBase.rawQuery("select nom, prenom from personnages order by nom", null);

        while (c.moveToNext()) {
            liste.add(c.getString(0)+" "+c.getString(1));
            System.out.println("nombre de ligne :"+c.getCount());
        }
        if(liste == null){
            liste.add("erreur de bdd !");
        }
        return liste;
    }

    public Personnages donneUnPerso(String choix){
        Personnages lePerso;

        String[] nomPrenom = choix.split(" ");

        String laRequete = "select id, prenom, nom, age, role, nomPhoto, idLieu, idRace from personnages where prenom like '"+nomPrenom[1]+"'";

        Cursor c = maBase.rawQuery(laRequete, null);

        if (c.moveToNext()){
            lePerso = new Personnages(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getString(5), c.getInt(6), c.getInt(7));
        } else {
            lePerso = new Personnages(0, "erreurBdd", "erreurBdd", 0, "erreurBdd", "erreurBdd", 0,0);
        }

        return lePerso;
    }

    public long ajouteLieu(Lieux lieu){
        ContentValues v = new ContentValues();
        v.put("id", lieu.getId());
        v.put("libelle", lieu.getLibelle());
        v.put("descriptif", lieu.getDescriptif());
        return maBase.insert("lieux",null, v);
    }

    public void supprimeLieux(){
        maBase.delete("lieux", null, null);
    }

    public Lieux donneUnLieu(int idLieu){
        Lieux lieu;

        String laRequete = "select id, libelle, descriptif from lieux where id like '"+idLieu+"'";

        Cursor c = maBase.rawQuery(laRequete, null);

        if (c.moveToNext()){
            lieu = new Lieux(c.getInt(0), c.getString(1), c.getString(2));
        } else {
            lieu = new Lieux(0, "erreurBdd", "erreurBdd");
        }

        return lieu;
    }

    public long ajouteRace(Races race){
        ContentValues v = new ContentValues();
        v.put("id", race.getId());
        v.put("libelle", race.getLibelle());
        v.put("descriptif", race.getDescriptif());
        return maBase.insert("races",null, v);
    }

    public void supprimeRaces(){
        maBase.delete("races", null, null);
    }

    public Races donneUneRace(int idRace){
        Races race;

        String laRequete = "select id, libelle, descriptif from races where id like '"+idRace+"'";

        Cursor c = maBase.rawQuery(laRequete, null);

        if (c.moveToNext()){
            race = new Races(c.getInt(0), c.getString(1), c.getString(2));
        } else {
            race = new Races(0, "erreurBdd", "erreurBdd");
        }

        return race;
    }
}
