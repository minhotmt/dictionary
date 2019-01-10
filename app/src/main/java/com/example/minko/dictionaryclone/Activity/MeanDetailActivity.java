package com.example.minko.dictionaryclone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minko.dictionaryclone.Model.Favorite;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.MyDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class MeanDetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    MyDatabase myDatabase;
    private TextToSpeech tts;
    private String word;
    private ArrayList<Favorite> lstFavorite;
    private String mean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        word = intent.getStringExtra("word");
        TextView txtWord = findViewById(R.id.txtWord);
        TextView txtMean = findViewById(R.id.txtMean);
        ImageButton imgListen = findViewById(R.id.imgListen);
        myDatabase = new MyDatabase(getApplicationContext());
        lstFavorite = myDatabase.getAllWord();
        for (Favorite item: lstFavorite){
            if (word.equals(item.getName())){
                mean = item.getDifinition();
            }
        }
        txtMean.setText(mean);
        txtWord.setText(word);
        tts = new TextToSpeech(getApplicationContext(), this);
        imgListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
//                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {
        tts.speak(word, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
