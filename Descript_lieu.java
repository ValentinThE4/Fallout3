package com.example.utilisateur.fallout3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Descript_lieu extends AppCompatActivity{

    TextView libelleTV;
    TextView descriptionTV;
    GestionBD sgbd = new GestionBD(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lieu);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        sgbd.open();
        Lieux leLieu = sgbd.donneUnLieu(id);

        libelleTV = (TextView) findViewById(R.id.nomLieu);
        descriptionTV = (TextView) findViewById(R.id.lieuDescription);

        libelleTV.setText(leLieu.getLibelle());
        descriptionTV.setText(leLieu.getDescriptif());
    }
}
