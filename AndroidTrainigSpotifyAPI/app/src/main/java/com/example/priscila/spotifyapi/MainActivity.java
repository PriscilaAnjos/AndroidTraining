package com.example.priscila.spotifyapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_ARTIST = 1;

    EditText dtSearchArtist;
    Button btnSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setObjects();
        setActions();
    }

    private void setObjects(){
        dtSearchArtist = (EditText) findViewById(R.id.searchArtist);
        btnSearchButton = (Button) findViewById(R.id.searchButton);
    }

    private void setActions(){

        btnSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Artist.class);
                intent.putExtra("artist_name", dtSearchArtist.getText().toString());
                startActivity(intent);
            }
        });

    }

}
