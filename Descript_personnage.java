package com.example.utilisateur.fallout3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Descript_personnage extends AppCompatActivity {

    private Integer[] imgPersoIds = {
            R.drawable.wadsworth, R.drawable.charon, R.drawable.nova,
            R.drawable.moirabrown, R.drawable.westonlesko, R.drawable.colinmoriarty,
            R.drawable.lucassimms, R.drawable.veraweatherly
    };

    ImageView imgPerso;
    TextView prenomTV;
    TextView nomTV;
    TextView ageTV;
    TextView roleTV;
    ArrayList<String> lesNoms = new ArrayList<String>();
    TextView lieuTV;
    TextView raceTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descript_personnages);

        GestionBD sgbd = new GestionBD(this);
        sgbd.open();
        lesNoms = sgbd.donneLesNoms();

        Intent intent = getIntent();
        int laPosition = intent.getIntExtra("leChoix", 0);

        Toast.makeText(this, "le choix : "+laPosition, Toast.LENGTH_LONG).show();
        Log.i("dans descript_Perso", "la position : "+laPosition);

        String persoChoisi = lesNoms.get(laPosition);
        Log.i("dans descript_Perso", "le nom : "+persoChoisi);

        final Personnages lechoix = sgbd.donneUnPerso(persoChoisi);

        Lieux leLieu = sgbd.donneUnLieu(lechoix.getId_lieu());
        Races laRace = sgbd.donneUneRace(lechoix.getId_race());

        imgPerso = (ImageView) findViewById(R.id.photoPers);
        imgPerso.setImageResource(imgPersoIds[laPosition]);

        prenomTV = (TextView) findViewById(R.id.prenom);
        nomTV = (TextView) findViewById(R.id.nom);
        ageTV = (TextView) findViewById(R.id.age);
        roleTV = (TextView) findViewById(R.id.role);

        lieuTV = (TextView) findViewById(R.id.lieu);
        raceTV = (TextView) findViewById(R.id.race);

        prenomTV.setText(lechoix.getPrenom());
        nomTV.setText(lechoix.getNom());

        if (lechoix.getAge() == 0){
            ageTV.setText("Indéterminé");
        } else {
            ageTV.setText(Integer.toString(lechoix.getAge()));
        }

        roleTV.setText(lechoix.getRole());

        SpannableString contentLieu = new SpannableString(leLieu.getLibelle());
        contentLieu.setSpan(new UnderlineSpan(), 0, contentLieu.length(), 0);
        lieuTV.setText(contentLieu);

        SpannableString contentRace = new SpannableString(laRace.getLibelle());
        contentRace.setSpan(new UnderlineSpan(), 0, contentRace.length(), 0);
        raceTV.setText(contentRace);

        raceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent descript_race = new Intent(getBaseContext(), Descript_race.class);
                descript_race.putExtra("id", lechoix.getId_race());
                startActivity(descript_race);
            }
        });

        lieuTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent descript_lieu = new Intent(getBaseContext(), Descript_lieu.class);
                descript_lieu.putExtra("id", lechoix.getId_lieu());
                startActivity(descript_lieu);
            }
        });
    }
}
