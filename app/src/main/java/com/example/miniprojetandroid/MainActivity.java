package com.example.miniprojetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Les composants utilisés dans l'appli
    EditText language;
    EditText query; // L'input de texte tout en haut, TODO : Trouver un meilleur nom, comprendre ce qu'il fait
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

        // On instancie les compsants
        language = findViewById(R.id.langage);
        query = findViewById(R.id.query);
        seekBarNombre = findViewById(R.id.seekBarNombre);
        adult = findViewById(R.id.adult);
        spinnerDate = findViewById(R.id.spinnerDate);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        envoyer = findViewById(R.id.envoyer);

        compteur = findViewById(R.id.compteur);
        compteur.setText(""+seekBarNombre.getProgress());
        // ...

        // Les listeners
        // Appui sur bouton envoyer, on envoie vers l'activité résultats

        seekBarNombre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                compteur.setText(""+seekBarNombre.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

             }
                                                 }


        );

        envoyer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String adultMovie = "false";
                if (adult.isChecked()) {
                    adultMovie = "true";
                    Log.d("Status films adultes", adultMovie);
                }
                Log.d("Status films adultes", adultMovie);

                Intent trouverFilms = new Intent(MainActivity.this, ResultActivity.class)
                        .putExtra("langage", language.getText().toString())
                        .putExtra("query", query.getText().toString())
                        .putExtra("pages", seekBarNombre.getProgress())
                        .putExtra("adult", adultMovie);

                startActivity(trouverFilms);
            }
        });

    }
}
