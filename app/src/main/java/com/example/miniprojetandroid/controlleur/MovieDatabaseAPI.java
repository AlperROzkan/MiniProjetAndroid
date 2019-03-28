package com.example.miniprojetandroid.controlleur;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.miniprojetandroid.modele.Film;

public interface MovieDatabaseAPI {

    @GET("search/movie/")
    Call<List<Film>> searchFilm(@Query("api_key") String api_key,
                                @Query("query") String query,
                                @Query("page") String page,
                                @Query("include_adult") String adult);

    @GET("changes/")
    Call<List<Film>> loadChanges(@Query("q") String status);

}
