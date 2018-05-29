package com.example.utilisateur.fallout3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListePersonnages extends ListActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> lesValeurs = new ArrayList<String>();
    ListView listNom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_personnages);

        GestionBD sgbd = new GestionBD(this);
        sgbd.open();

        lesValeurs = sgbd.donneLesNoms();
        Log.i("liste2","les noms : "+lesValeurs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesValeurs);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        sgbd.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent descript_personnage = new Intent(this, Descript_personnage.class);
        Log.i("clic", "après avoir cliqué : "+position);
        Toast.makeText(this, "position : "+position, Toast.LENGTH_LONG).show();
        descript_personnage.putExtra("leChoix", position);
        startActivity(descript_personnage);
    }
}
