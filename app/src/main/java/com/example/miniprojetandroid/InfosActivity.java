package com.example.miniprojetandroid;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miniprojetandroid.modele.Film;
import com.example.miniprojetandroid.modele.Films;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class InfosActivity extends AppCompatActivity {

    ImageView poster;
    TextView titre;
    TextView annee;
    TextView resume;
    Film film;
    Button retour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        Bundle extras = getIntent().getExtras();
        film = (Film)extras.get("film");


        poster = findViewById(R.id.posterFilm);
        titre = findViewById(R.id.titreFilm);
        annee = findViewById(R.id.anneeFilm);
        resume = findViewById(R.id.descriFilm);
        retour = findViewById(R.id.retour);



       titre.setText(film.getTitle());
       annee.setText(film.getReleaseDate());
       resume.setText(film.getOverview());
        if (!film.getPosterPath().equals("null")) {
            try {
              new AffichePoster().execute(new URL(("https://image.tmdb.org/t/p/w500"+film.getPosterPath()).replace("\"", "")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    public class AffichePoster extends AsyncTask<URL, Integer, Drawable> {
       public AffichePoster() {
            super();

        }

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
            poster.setImageDrawable(drawable);
        }
    }

}
