package com.meetandeat.meetandeat;

import android.app.Activity;
import android.os.Bundle;

import android.widget.Button;



public class buttonActivity extends Activity {
    private String buttonText;
    private Button readingBtn;
    private Button hikingBtn;
    private Button gamesBtn;
    private Button socialBtn;
    private Button musicBtn;
    private Button cookingBtn;
    private Button campingBtn;
    private Button dancingBtn;
    private Button writingBtn;
    private Button paintingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.content_button );

        readingBtn = (Button)findViewById( R.id.readingBtn );
        buttonText = readingBtn.getText().toString();

        hikingBtn = (Button)findViewById( R.id.hikingBtn );
        buttonText = hikingBtn.getText().toString();

        gamesBtn = (Button)findViewById( R.id.gamesBtn );
        buttonText = gamesBtn.getText().toString();

        socialBtn = (Button)findViewById( R.id.socialBtn );
        buttonText = socialBtn.getText().toString();

        musicBtn = (Button)findViewById( R.id.musicBtn );
        buttonText = musicBtn.getText().toString();

    }

}
