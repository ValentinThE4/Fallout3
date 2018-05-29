package com.example.utilisateur.fallout3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgIntro;
    TextView intro;
    Button bStart;
    private List<Personnages> lesPersonnages;
    TraitementJSON traiteJSON;
    TraitementJSONLieux traiteJSONLieux;
    TraitementJSONRaces traiteJSONRaces;
    GestionBD sgbd;
    ArrayList<String> lesNoms = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lesPersonnages = new ArrayList<Personnages>();

        traiteJSON = new TraitementJSON(this);
        traiteJSONLieux = new TraitementJSONLieux(this);
        traiteJSONRaces = new TraitementJSONRaces(this);

        sgbd = new GestionBD(this);
        sgbd.open();
        sgbd.supprimePersonnages();
        sgbd.supprimeLieux();
        sgbd.supprimeRaces();

        traiteJSON.execute("https://thibaut-valentin-blog-bts-sio.000webhostapp.com/bddJSON/fichierJSONbddPers.json");
        traiteJSONLieux.execute("https://thibaut-valentin-blog-bts-sio.000webhostapp.com/bddJSON/fichierJSONbddLieu.json");
        traiteJSONRaces.execute("https://thibaut-valentin-blog-bts-sio.000webhostapp.com/bddJSON/fichierJSONbddRace.json");

        sgbd.close();

        imgIntro = (ImageView) findViewById(R.id.imgIntro);
        imgIntro.setImageResource(R.drawable.fallout3);

        intro = (TextView) findViewById(R.id.intro);
        intro.setText("Cet application recense certains personnages du jeu Fallout 3. \n Une description de chaques personnages présents en est faites ainsi qu'une description de leur race et de leur lieu d'appariton.");

        bStart = (Button) findViewById(R.id.buttonStart);
        bStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent liste = new Intent(this, ListePersonnages.class);
        startActivity(liste);
    }
}
