package com.example.priscila.spotifyapi;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CdAndTracksAdapter extends ArrayAdapter<CdAndTracksModel>{

    private static final String LOG_TAG = CdAndTracksAdapter.class.getSimpleName();

    public CdAndTracksAdapter(Activity context, ArrayList<CdAndTracksModel> CdAndTracksModel){
        super(context, 0, CdAndTracksModel);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_top_track_information, parent, false);
        }

        CdAndTracksModel trackAtual = getItem(position);

        TextView nomeTrackView = (TextView) listItemView.findViewById(R.id.nomeTrack);
        nomeTrackView.setText(trackAtual.getmNome());

        TextView numeroFaixaView = (TextView) listItemView.findViewById(R.id.numeroTrack);
        numeroFaixaView.setText(trackAtual.getmNumeroFaixa());

        TextView popularidadeTrackView = (TextView) listItemView.findViewById(R.id.popularidade);
        popularidadeTrackView.setText(trackAtual.getmPopularidade());

        ImageView imageCdView = (ImageView) listItemView.findViewById(R.id.capaCd);

        Log.e(LOG_TAG, "NOME  " + trackAtual.getmNome() + "\nNUMERO " + trackAtual.getmNumeroFaixa()
                + "\nPOPULARIDADE" + trackAtual.getmPopularidade() + "\nIMAGEM" +
                trackAtual.getmCapaCd());

        return listItemView;

    }
}
