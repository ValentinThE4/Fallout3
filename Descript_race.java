package com.example.utilisateur.fallout3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Descript_race extends AppCompatActivity {

    TextView libelleTV;
    TextView descriptionTV;
    GestionBD sgbd = new GestionBD(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.race);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        sgbd.open();
        Races laRace = sgbd.donneUneRace(id);

        libelleTV = (TextView) findViewById(R.id.nomRace);
        descriptionTV = (TextView) findViewById(R.id.raceDescription);

        libelleTV.setText(laRace.getLibelle());
        descriptionTV.setText(laRace.getDescriptif());
    }
}
