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

public class MainActivity extends AppCompatActivity {

    // Les composants utilisés dans l'appli
    Button envoyer;
    EditText keywords; // L'input de texte tout en haut, TODO : Trouver un meilleur nom, comprendre ce qu'il fait
    Spinner spinnerDate;
    Spinner spinnerGenre;
    SeekBar seekBarNombre;
    // TODO : Ajouter des composants et fonctionalités, dans de nouvelles activités ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On instancie les compsants
        envoyer = findViewById(R.id.envoyer);
        keywords = findViewById(R.id.keywords);
        spinnerDate = findViewById(R.id.spinnerDate);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        seekBarNombre = findViewById(R.id.seekBarNombre);
        // ...
        Log.e("MainActivity 1", "oncreate");


        // Les listeners
        // Appui sur bouton envoyer, on envoie vers l'activité résultats
        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity 2", "envoyer listener");

                Intent trouverFilms = new Intent(MainActivity.this, ResultActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, keywords.getText());
                startActivity(trouverFilms);
            }
        });

    }
}
