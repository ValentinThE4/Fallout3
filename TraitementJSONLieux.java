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

public class TraitementJSONLieux extends AsyncTask<String, Void, Boolean>{

    List<Lieux> lesLieux = new ArrayList<Lieux>();

    Context context;
    JSONObject jObj = null;
    URL url;
    HttpURLConnection connexion;
    GestionBD sgbd;

    public TraitementJSONLieux(Context context){
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
            String ficLieux;
            ficLieux = lectureFichierDistant();
            Log.i("doInBack","le fichier distant : "+ficLieux);

            JSONObject jsonLieux = parseLieux(ficLieux);
            Log.i("doInBack", "le fichier json : "+jsonLieux);

            recLieux(jsonLieux);

            Log.i("doInBack", "nombre de lieux : "+lesLieux.size());
            int num = 1;
            StringBuilder message = new StringBuilder("les descript_lieux : ");
            long id;

            for (Lieux lieu : lesLieux){
                message.append((lieu.getId()));
                message.append(" : ");
                message.append(lieu.getLibelle());
                message.append(" : ");
                message.append(lieu.getDescriptif());
                message.append("\n");
                num++;
                id = sgbd.ajouteLieu(lieu);
            }

            Log.i("doInBack", "message : "+message);
            sgbd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void recLieux(JSONObject jsonLieux){

        JSONArray lesLieuxArr = null;

        try{
            lesLieuxArr = jsonLieux.getJSONArray("lieux");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("reclieux","erreurJSArray");
        }

        for(int i = 0; i < lesLieuxArr.length(); i++){
            JSONObject nuplet = null;
            String id, libelle, descriptif;
            Long idBD;
            Lieux lieu;

            try{
                nuplet = lesLieuxArr.getJSONObject(i);

                id = nuplet.getString("id");
                libelle = nuplet.getString("libelle");
                descriptif = nuplet.getString("descriptif");

                lieu = new Lieux(Integer.parseInt(id), libelle, descriptif);

                lesLieux.add(lieu);

            } catch (JSONException e){
                e.printStackTrace();

            }
        }
    }

    private JSONObject parseLieux(String textLieux){

        if(textLieux != null){

            try{
                jObj = new JSONObject(textLieux);
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

    public List<Lieux> getLesLieux(){
        return lesLieux;
    }

}
