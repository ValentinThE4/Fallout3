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

public class TraitementJSON extends AsyncTask<String, Void, Boolean>{

    List<Personnages> lesPersonnages = new ArrayList<Personnages>();

    Context context;
    JSONObject jObj = null;
    URL url;
    HttpURLConnection connexion;
    GestionBD sgbd;

    public TraitementJSON(Context context){
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
            String ficPersonnages;
            ficPersonnages = lectureFichierDistant();
            Log.i("doInBack","le fichier distant : "+ficPersonnages);

            JSONObject jsonPersonnages = parsePersonnages(ficPersonnages);
            Log.i("doInBack", "le fichier json : "+jsonPersonnages);

            recPersonnages(jsonPersonnages);

            Log.i("doInBack", "nombre de persos : "+lesPersonnages.size());
            int num = 1;
            StringBuilder message = new StringBuilder("les descript_personnages : ");
            long id;

            for (Personnages perso : lesPersonnages){
                message.append((perso.getId()));
                message.append(" : ");
                message.append(perso.getPrenom());
                message.append(" : ");
                message.append(perso.getNom());
                message.append(" : ");
                message.append(perso.getAge());
                message.append(" : ");
                message.append(perso.getRole());
                message.append(" : ");
                message.append(perso.getNom_photo());
                message.append(" : ");
                message.append(perso.getId_lieu());
                message.append(" : ");
                message.append(perso.getId_race());
                message.append("\n");
                num++;
                id = sgbd.ajoutePers(perso);
            }

            Log.i("doInBack", "message : "+message);
            sgbd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void recPersonnages(JSONObject jsonPersonnages){

        JSONArray lesPersos = null;

        try{
            lesPersos = jsonPersonnages.getJSONArray("personnages");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("recpers","erreurJSArray");
        }

        for(int i = 0; i < lesPersos.length(); i++){
            JSONObject nuplet = null;
            String prenom, nom, role, nomPhoto, id, age, idLieu, idRace;
            Long idBD;
            Personnages perso;

            try{
                nuplet = lesPersos.getJSONObject(i);

                id = nuplet.getString("id");
                prenom = nuplet.getString("prenom");
                nom = nuplet.getString("nom");
                age = nuplet.getString("age");
                role = nuplet.getString("role");
                nomPhoto = nuplet.getString("nomPhoto");
                idLieu = nuplet.getString("id_Lieu");
                idRace = nuplet.getString("id_Race");

                perso = new Personnages(Integer.parseInt(id), prenom, nom, Integer.parseInt(age), role, nomPhoto, Integer.parseInt(idLieu), Integer.parseInt(idRace));

                lesPersonnages.add(perso);

            } catch (JSONException e){
                e.printStackTrace();

            }
        }
    }

    private JSONObject parsePersonnages(String textPersonnages){

        if(textPersonnages != null){

            try{
                jObj = new JSONObject(textPersonnages);
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

    public List<Personnages> getLesPersonnages(){
        return lesPersonnages;
    }

    public String getLesNoms(){
        String liste = "";
        for (Personnages perso : lesPersonnages){
            liste += perso.getNom()+"\n";
        }
        return liste;
    }
}
