package com.example.miniprojetandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResultActivity extends AppCompatActivity {

    private AdapteurResult itemsAdapter;
    private String[] films = {"1","2","3"};

    private LinearLayout ll;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ll = (LinearLayout) findViewById(R.id.lineLay);

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
            String idxStr = (String)v.getTag();
            (Toast.makeText(v.getContext(), idxStr, Toast.LENGTH_SHORT)).show();
        }
    };
}
