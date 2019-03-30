package com.example.miniprojetandroid.network;
import android.content.Context;
import android.util.Log;

import com.example.miniprojetandroid.modele.Film;
import com.example.miniprojetandroid.modele.Films;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class GetTheMovieDB {

    final static private String API_KEY = "9085d90557e91e1d3531eab4a3510300";

    public static void getMoviesDiscover(Context context) {
        Ion.with(context)
                .load("https://api.themoviedb.org/3/discover/movie" +
                        "?api_key=" + API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.e("YES", result.toString());
                    }
                });
    }
}
