package com.example.utilisateur.fallout3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TraitementJSONRaces extends AsyncTask <String, Void, Boolean> {

    List<Races> lesRaces = new ArrayList<Races>();

    Context context;
    JSONObject jObj = null;
    URL url;
    HttpURLConnection connexion;
    GestionBD sgbd;

    public TraitementJSONRaces(Context context){
        this.context = context;
        sgbd = new GestionBD(context);
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        Log.i("doInBack", "le départ : ");
        sgbd.open();

        try {
            url = new URL(urls[0]);
            Log.i("doInBack", "le fichier distant : "+urls[0]);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        Boolean result = false;

        try {
            String ficRaces;
            ficRaces = lectureFichierDistant();
            Log.i("doInBack","le fichier distant : "+ficRaces);

            JSONObject jsonRaces = parseRaces(ficRaces);
            Log.i("doInBack", "le fichier json : "+jsonRaces);

            recRaces(jsonRaces);

            Log.i("doInBack", "nombre de lieux : "+lesRaces.size());
            int num = 1;
            StringBuilder message = new StringBuilder("les descript_lieux : ");
            long id;

            for (Races race : lesRaces){
                message.append((race.getId()));
                message.append(" : ");
                message.append(race.getLibelle());
                message.append(" : ");
                message.append(race.getDescriptif());
                message.append("\n");
                num++;
                id = sgbd.ajouteRace(race);
            }

            Log.i("doInBack", "message : "+message);
            sgbd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void recRaces(JSONObject jsonRaces){

        JSONArray lesRacesArr = null;

        try{
            lesRacesArr = jsonRaces.getJSONArray("races");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("recraces","erreurJSArray");
        }

        for(int i = 0; i < lesRacesArr.length(); i++){
            JSONObject nuplet = null;
            String id, libelle, descriptif;
            Long idBD;
            Races race;

            try{
                nuplet = lesRacesArr.getJSONObject(i);

                id = nuplet.getString("id");
                libelle = nuplet.getString("libelle");
                descriptif = nuplet.getString("descriptif");

                race = new Races(Integer.parseInt(id), libelle, descriptif);

                lesRaces.add(race);

            } catch (JSONException e){
                e.printStackTrace();

            }
        }
    }

    private JSONObject parseRaces(String textRaces){

        if(textRaces != null){

            try{
                jObj = new JSONObject(textRaces);
            }catch(JSONException e){
                e.printStackTrace();
                Log.i("parper", "erreurJSObj");
            }
            return jObj;

        } else {
            return null;
        }
    }

    private String lectureFichierDistant(){

        StringBuilder builder = new StringBuilder();

        try{
            connexion = (HttpURLConnection) url.openConnection();
        } catch (IOException e){
            e.printStackTrace();
        }

        String line;
        BufferedReader br = null;

        try{
            br = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
        } catch (IOException e1){
            e1.printStackTrace();
        }

        try{
            while ((line = br.readLine()) != null){
                builder.append(line).append("\n");
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return builder.toString();
    }

    public List<Races> getLesRaces(){
        return lesRaces;
    }
}
