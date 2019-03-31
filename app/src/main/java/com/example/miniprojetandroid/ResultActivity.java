package com.example.miniprojetandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private LinearLayout ll;
    private TextView test;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        context = getApplicationContext();

        ll = (LinearLayout) findViewById(R.id.lineLay);
        test = findViewById(R.id.test1);

        Bundle extras = getIntent().getExtras();
        String valeur = extras.getString(Intent.EXTRA_TEXT);
        test.setText(valeur);

        // Une liste de films
        Films mesFilms = new Films();

        // Requete pour recuperer les films selon un mot clé précis
        Ion.with(this)
                .load("https://api.themoviedb.org/3/search/movie" +
                        "?api_key=" + api_key +
                        "&language=" + extras.getString("langage", "en-US") +
                        "&query=" + extras.getString("query") +
                        "&page=" + extras.getString("pages", "1") +
                        "&include_adult=" + extras.getString("adult", "true"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Iterator<JsonElement> listFilms = result.getAsJsonArray("results").iterator();
                        JsonElement film;

                        Log.e("URL", "https://api.themoviedb.org/3/search/movie" +
                                "?api_key=" + api_key +
                                "&language=" + extras.getString("langage", "en-US") +
                                "&query=" + extras.getString("query") +
                                "&page=" + extras.getString("pages", "1") +
                                "&include_adult=" + extras.getString("adult", "true"));

                        while (listFilms.hasNext()) {
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
                        }

                        for(int i = 0; i < mesFilms.size(); i++){
                            try {
                                if (!mesFilms.get(i).getPosterPath().equals("null")) new TacheAffiche().execute(new URL(("https://image.tmdb.org/t/p/w500"+mesFilms.get(i).getPosterPath()).replace("\"", "")));
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }
                        }


                        System.out.println(mesFilms.toString());
                    }
                });

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
    public class TacheAffiche extends AsyncTask<URL, Integer, Drawable> {
        ImageButton imgBtn = new ImageButton(context);

        @Override
        protected Drawable doInBackground(URL... urls) {

            try {
                InputStream is = urls[0].openStream();
                Drawable d = Drawable.createFromStream(is, "src");
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            imgBtn.setImageDrawable(drawable);
            imgBtn.setOnClickListener(buttonClick);
            ll.addView(imgBtn);
            int idx = ll.indexOfChild(imgBtn);
            imgBtn.setTag(Integer.toString(idx));
        }
    }
}
