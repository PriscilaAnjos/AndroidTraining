package com.example.priscila.spotifyapi;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CdAndTracks extends AppCompatActivity {

    private String TAG = CdAndTracks.class.getSimpleName();
    private ListView lista;

    private CdAndTracksAdapter cdAndTracksAdapter;

    private static final String urlApiTopTrack = "https://api.spotify.com/v1/artists/";
    private static final String urlActionTopTrack = "/top-tracks?country=BR&offset=0&limit=3 ";

    private String nomeCd;
    private String capaCd;
    private String nomeTrack;
    private String numeroTrack;
    private String popularidade;

    private String artistId;

    public static ArrayList<CdAndTracksModel> tracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd_and_tracks);

        if(getIntent().hasExtra("artist_id")){
            Bundle extras = getIntent().getExtras();
            artistId = extras.getString("artist_id");

            Log.e(TAG, "ID - " + artistId);
        }

        setActions();
        viewController();
    }

    private void viewController(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setActions(){

        lista = (ListView) findViewById(R.id.list_activity_cd_and_tracks);
        new CdAndTracks.GetTracks().execute();

    }

    private void setAdapter(){
        cdAndTracksAdapter = new CdAndTracksAdapter(this, tracks);
        ListView list = (ListView) findViewById(R.id.list_activity_cd_and_tracks);
        list.setAdapter(cdAndTracksAdapter);
    }

    private class GetTracks extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(CdAndTracks.this, "JSON fazendo downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... args){

            HttpHandler httpH = new HttpHandler();
            String jsonStr = httpH.makeServiceCall(urlApiTopTrack + artistId + urlActionTopTrack);

            Log.e(TAG, "Response from url " + jsonStr);

            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArrTrack = jsonObj.getJSONArray("tracks");

                    for(int i = 0; i < 3; i++){
                        JSONObject jsonObjTrack = jsonArrTrack.getJSONObject(i);

                        //album
                        JSONObject jsonObjAlbum = jsonObjTrack.getJSONObject("album");
                        nomeCd = jsonObjAlbum.getString("name");

                        //nome Faixa
                        nomeTrack = jsonObjTrack.getString("name");

                        //numero da faixa
                        numeroTrack = jsonObjTrack.getString("track_number");

                        //popularidade
                        popularidade = jsonObjTrack.getString("popularity");

                        //capa
                        JSONArray imageArrJson = jsonObjAlbum.getJSONArray("images");
                        JSONObject imageObjJson = imageArrJson.getJSONObject(i);
                        capaCd = imageObjJson.getString("url");

                        tracks.add(new CdAndTracksModel(nomeTrack, numeroTrack, popularidade, capaCd));

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

            Log.e(TAG, "TRACKS ONPOSTEXECUTE " + tracks);

            setAdapter();
        }
    }


}
