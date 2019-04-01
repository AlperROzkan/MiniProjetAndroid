package com.example.miniprojetandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojetandroid.modele.Film;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapteurResult extends ArrayAdapter<Film> {

    public AdapteurResult(Context context, ArrayList<Film> items) {
        super(context, R.layout.affichage_poster, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        row = inflater.inflate(R.layout.affichage_poster, null);
        TextView txt = row.findViewById(R.id.titrePoster);
        Film f = getItem(position);
        txt.setText(f.getTitle());


                try {
            if (!f.getPosterPath().equals("null")) new TacheAffiche(row, f).execute(new URL(("https://image.tmdb.org/t/p/w500"+f.getPosterPath()).replace("\"", "")));
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        return row;
    }

    public class TacheAffiche extends AsyncTask<URL, Integer, Drawable> {
        public View row;
        ImageButton imgBtn;
        public Film film;


        public TacheAffiche(android.view.View row, Film f) {
            super();
            this.row = row;
            this.film = f;
            this.imgBtn = row.findViewById(R.id.filmPoster);
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
            imgBtn.setImageDrawable(drawable);
            imgBtn.setOnClickListener(buttonClick);
            imgBtn.setTag(Integer.toString(film.getId()));

        }

    }


    View.OnClickListener buttonClick = new View.OnClickListener() {
        public void onClick(View v) {
            String idxStr = (String) v.getTag();
            (Toast.makeText(v.getContext(), idxStr, Toast.LENGTH_SHORT)).show();
        }
    };

}
