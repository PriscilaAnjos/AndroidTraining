package com.example.priscila.spotifyapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Artist extends AppCompatActivity {

    TextView popularity;
    TextView type;
    TextView artistName;
    ImageView artistPicture;
    Button cdAndTracks;

    String getArtistName;
    String artistNameForSearch;

    private String TAG = Artist.class.getSimpleName();
    private static final String urlAPI = "https://api.spotify.com/v1/search?q=";
    private static final String urltype = "&type=artist&offset=0&limit=1";

    private String nome;
    private String popularidade;
    private String tipo;
    private String image;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        if(getIntent().hasExtra("artist_name")){
            Bundle extras = getIntent().getExtras();
            getArtistName = extras.getString("artist_name");
            artistNameForSearch = getArtistName.toLowerCase().replaceAll("(^ )|( $)", "").replaceAll(" ", "%20");
        }

        setObjects();
        setActions();
    }

    private void setObjects(){

        popularity = (TextView) findViewById(R.id.popularity);
        type = (TextView) findViewById(R.id.type);
        artistName = (TextView) findViewById(R.id.artistName);
        artistPicture = (ImageView) findViewById(R.id.artistPicture);
        cdAndTracks = (Button) findViewById(R.id.cdAndTrack);

    }

    private void setActions(){

        new GetArtists().execute();

        cdAndTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CdAndTracks.class);
                intent.putExtra("artist_id", id);
                startActivity(intent);
            }
        });

    }

    private void setObjectsContent(){

        popularity.setText(popularidade);
        type.setText(tipo);
        artistName.setText(nome);

        LoadImageFromURL loadImage = new LoadImageFromURL();
        loadImage.execute();
    }

    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(image);
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            artistPicture.setImageBitmap(result);
        }
    }

    private class GetArtists extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(Artist.this, "JSON fazendo downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... args){

            HttpHandler httpH = new HttpHandler();

            String jsonStr = httpH.makeServiceCall(urlAPI + getArtistName + urltype);

            Log.e(TAG, "Response from url " + jsonStr);

            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject jsonArtist = jsonObj.getJSONObject("artists");
                    JSONArray jsonItems = jsonArtist.getJSONArray("items");

                    for(int i = 0; i < jsonItems.length(); i++){
                        JSONObject jsonArtistInformation = jsonItems.getJSONObject(i);

                        nome = jsonArtistInformation.getString("name");
                        popularidade = jsonArtistInformation.getString("popularity");
                        tipo = jsonArtistInformation.getString("type");
                        id = jsonArtistInformation.getString("id");

                        JSONArray imageArrJson = jsonArtistInformation.getJSONArray("images");
                        JSONObject imageObjJson = imageArrJson.getJSONObject(i);

                        image = imageObjJson.getString("url");

                    }

                }catch (final JSONException e){
                    Log.e(TAG, "JSON parsing error 1: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Jason parsing error 2 " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else{
                Log.e(TAG, "Não consegui retorno do JASON.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Não consegui retorno do JASON. verifique o LogCat.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            setObjectsContent();
        }

    }

}