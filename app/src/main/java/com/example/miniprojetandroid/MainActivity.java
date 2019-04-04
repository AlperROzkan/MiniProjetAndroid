package com.example.miniprojetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojetandroid.modele.Genre;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String api_key = "9085d90557e91e1d3531eab4a3510300";

    // Les composants utilisés dans l'appli
    EditText language;
    EditText query; // L'input de texte tout en haut
    SeekBar seekBarNombre;
    Switch adult;
    Spinner spinnerDate;
    Spinner spinnerGenre;
    Button envoyer;

    TextView compteur;

    // TODO : Ajouter des composants et fonctionalités, dans de nouvelles activités ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On instancie les composants
        language = findViewById(R.id.langage);
        query = findViewById(R.id.query);
        seekBarNombre = findViewById(R.id.seekBarNombre);
        adult = findViewById(R.id.adult);
        spinnerDate = findViewById(R.id.spinnerDate);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        envoyer = findViewById(R.id.envoyer);

        compteur = findViewById(R.id.compteur);
        compteur.setText("" + seekBarNombre.getProgress());
        // ...

        ArrayList<String> genres = new ArrayList<>(); // Pour stocker les genres des films

        // Requete pour peupler les genres
        Ion.with(this)
                .load("https://api.themoviedb.org/3/genre/movie/list?api_key=9085d90557e91e1d3531eab4a3510300&language=en-US")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // On parcourt la liste de genres afin de peupler notre array list qui nous servira pour l'affichage
                        Iterator<JsonElement> listGenres = result.getAsJsonArray("genres").iterator();
                        JsonElement genre; // Un genre de la liste que l'on recupere depuis l'api

                        while (listGenres.hasNext()) {
                            genre = listGenres.next();
                            genres.add(genre.getAsJsonObject().get("name").getAsString()); // ICI ELIOTT
                        }
                    }
                });

        // On remplit les années dans le spinner
        ArrayList<Integer> annee = new ArrayList<>();
        for (int i = 2019; i > 1950; i--) {
            annee.add(i);
        }

        // Nous ajoutons les genres recuperés au spinner de l'application
        ArrayAdapter<Integer> yearArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, annee);
        spinnerDate.setAdapter(yearArrayAdapter);

        // Nous ajoutons les genres recuperés au spinner de l'application
        ArrayAdapter<String> genreArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genres);
        spinnerGenre.setAdapter(genreArrayAdapter);

        // Les listeners

        // Appui sur bouton envoyer, on envoie vers l'activité résultats
        seekBarNombre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                compteur.setText("" + seekBarNombre.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Pour passer a la liste de resultats
        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gerer la demande de films pour adultes
                String adultMovie = "false";
                if (adult.isChecked()) {
                    adultMovie = "true";
                }

                // Recuperer le genre selectionné
                Log.e("date", spinnerDate.getSelectedItem().toString());

                if (seekBarNombre.getProgress() == 0) {
                    Toast.makeText(MainActivity.this, "Il faut plus de 0 films", Toast.LENGTH_LONG).show();
                } else {
                    Intent trouverFilms = new Intent(MainActivity.this, ResultActivity.class)
                            .putExtra("langage", language.getText().toString())
                            .putExtra("query", query.getText().toString())
                            .putExtra("nombre", seekBarNombre.getProgress())
                            .putExtra("adult", adultMovie)
                            .putExtra("date", spinnerDate.getSelectedItem().toString());
                    startActivity(trouverFilms);
                }

            }
        });

    }
}
