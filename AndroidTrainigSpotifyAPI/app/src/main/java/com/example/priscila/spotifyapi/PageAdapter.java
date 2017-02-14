package com.example.priscila.spotifyapi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm) {super(fm);}

    private String tabTitles[] = new String[] {"Top 1", "Top 2", "Top 3"};

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstCdAndTrack();
            case 1:
                return new SecondCdAndTrack();
            case 2:
                return new ThirdCdAndTrack();
            default:
                return null;
        }
    }

    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

}

