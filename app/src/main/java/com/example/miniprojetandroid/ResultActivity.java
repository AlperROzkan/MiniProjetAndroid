package com.example.miniprojetandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private AdapteurResult itemsAdapter;
    private String[] films = {"1", "2", "3"};

    private LinearLayout ll;
    private TextView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ll = (LinearLayout) findViewById(R.id.lineLay);
        test = findViewById(R.id.test1);

        Bundle extras = getIntent().getExtras();
        String valeur = extras.getString(Intent.EXTRA_TEXT);
        test.setText(valeur);

        // Une liste de films
        Films mesFilms = new Films();

        // Requete pour recuperer les films
        Ion.with(this)
                .load("https://api.themoviedb.org/3/discover/movie?api_key=9085d90557e91e1d3531eab4a3510300")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Iterator<JsonElement> listFilms = result.getAsJsonArray("results").iterator();
                        JsonElement film;

                        while (listFilms.hasNext()) {
                            film = listFilms.next();

                            mesFilms.add(new Film(
                                    film.getAsJsonObject().get("vote_count").getAsInt(),
                                    film.getAsJsonObject().get("id").getAsInt(),
                                    film.getAsJsonObject().get("vote_average").getAsDouble(),
                                    film.getAsJsonObject().get("title").getAsString(),
                                    film.getAsJsonObject().get("popularity").getAsDouble(),
                                    film.getAsJsonObject().get("poster_path").getAsString(),
                                    film.getAsJsonObject().get("adult").getAsBoolean(),
                                    film.getAsJsonObject().get("overview").getAsString(),
                                    film.getAsJsonObject().get("release_date").getAsString()
                            ));
                        }

                        // FAIRE DES MODIFS SUR L'ACTIVITE ICI

                    }
                });

        /*for(int i = 0; i < films.length; i++) {
            ImageButton imgBtn = new ImageButton(this);
            try {
                imgBtn.setImageDrawable(drawableFromUrl(new URL("https://vignette.wikia.nocookie.net/fwob/images/c/cc/Yuri.png/revision/latest?cb=20180619162817")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgBtn.setOnClickListener(buttonClick);
            ll.addView(imgBtn);
            int idx = ll.indexOfChild(imgBtn);
            imgBtn.setTag(Integer.toString(idx));
        }*/
    }


    public static Drawable drawableFromUrl(URL url) {
        try {
            InputStream is = url.openStream();
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    View.OnClickListener buttonClick = new View.OnClickListener() {
        public void onClick(View v) {
            String idxStr = (String) v.getTag();
            (Toast.makeText(v.getContext(), idxStr, Toast.LENGTH_SHORT)).show();
        }
    };
}
