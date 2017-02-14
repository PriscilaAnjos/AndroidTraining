package com.example.priscila.spotifyapi;

/**
 * Created by Priscila on 26/01/2017.
 */

public class CdAndTracksModel {

    String mNome;
    String mNumeroFaixa;
    String mPopularidade;
    String mCapaCd;

    public CdAndTracksModel(String vNome, String vNumeroFaixa, String vPopularidade, String vCapaCd) {
        mNome = vNome;
        mNumeroFaixa = vNumeroFaixa;
        mPopularidade = vPopularidade;
        mCapaCd = vCapaCd;
    }

    public String getmNome() {
        return mNome;
    }

    public String getmNumeroFaixa() {
        return mNumeroFaixa;
    }

    public String getmPopularidade() {
        return mPopularidade;
    }

    public String getmCapaCd() {
        return mCapaCd;
    }
}
