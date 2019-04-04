package com.example.miniprojetandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View;
import android.widget.LinearLayout;

import com.example.miniprojetandroid.modele.Film;
import com.example.miniprojetandroid.modele.Films;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class ResultActivity extends AppCompatActivity {

    final String api_key = "9085d90557e91e1d3531eab4a3510300";

    private AdapteurResult itemsAdapter;
    private String[] films = {"1", "2", "3"};

    private GridView listView;

    private static Context context;
    private Button retour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        context = getApplicationContext();
        retour = findViewById(R.id.retour2);
        listView = (GridView) findViewById(R.id.lineLay);

        // On recupere les valeurs de l'activité d'avant
        Bundle extras = getIntent().getExtras();


        Films mesFilms = new Films(); // Une liste de films
        final int nombreResultats = extras.getInt("nombre"); // Nombre de resultats

        // On a un adapteur pour afficher les films
        final AdapteurResult itemsAdapter = new AdapteurResult(this, mesFilms);
        listView.setAdapter(itemsAdapter);

        // Requete pour recuperer les films selon un mot clé précis
        Ion.with(this)
                .load("https://api.themoviedb.org/3/search/movie" +
                        "?api_key=" + api_key +
                        "&language=" + extras.getString("langage", "en-US") +
                        "&query=" + extras.getString("query") +
                        "&year=" + extras.getString("date") +
                        "&include_adult=" + extras.getString("adult", "true"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.has("errors") && result.get("errors").getAsString().equals("query must be provided")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Aucun resultat sans query !", Toast.LENGTH_SHORT);
                            toast.show();

                        } else {
                            Iterator<JsonElement> listFilms = result.getAsJsonArray("results").iterator();
                            JsonElement film;

                            Log.e("URL", "https://api.themoviedb.org/3/search/movie" +
                                    "?api_key=" + api_key +
                                    "&language=" + extras.getString("langage", "en-US") +
                                    "&query=" + extras.getString("query") +
                                    "&year=" + extras.getString("date") +
                                    "&include_adult=" + extras.getString("adult", "true"));

                            int i = 0;
                            while (listFilms.hasNext() && i < nombreResultats) {
                                film = listFilms.next();

                                mesFilms.add(new Film(
                                        film.getAsJsonObject().get("vote_count").getAsInt(),
                                        film.getAsJsonObject().get("id").getAsInt(),
                                        film.getAsJsonObject().get("vote_average").getAsDouble(),
                                        film.getAsJsonObject().get("title").toString(),
                                        film.getAsJsonObject().get("popularity").getAsDouble(),
                                        film.getAsJsonObject().get("poster_path").toString(),
                                        film.getAsJsonObject().get("adult").getAsBoolean(),
                                        film.getAsJsonObject().get("overview").toString(),
                                        film.getAsJsonObject().get("release_date").toString()
                                ));
                                i++;
                            }

                            itemsAdapter.notifyDataSetChanged();
                        }
                    }
                });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
