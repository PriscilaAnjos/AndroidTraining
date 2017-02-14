package com.example.priscila.spotifyapi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FirstCdAndTrack extends Fragment {

    private String TAG = FirstCdAndTrack.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setObject();

        return inflater.inflate(R.layout.fragment_first_cd_and_track, container, false);

    }

    private void setObject(){
        ArrayList<CdAndTracksModel> list = CdAndTracks.tracks;

        Log.e(TAG, "DATA FIRST CD AND TRACKS  " + CdAndTracks.tracks);


        for(CdAndTracksModel obj : list){
            Log.d("DATA", obj.getmNome() + " " + obj.getmNumeroFaixa());

            Log.e(TAG, "DATA FIRST CD AND TRACKS  " + obj.getmNome());

        }
    }
}
