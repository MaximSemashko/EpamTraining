package com.example.epamtraining;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GoogleFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_COUNT = 3;

    public GoogleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;
        if (pos == 0) {
            fragment = new GoogleNewEpisodesFragment();
        } else if (pos == 1) {
            fragment = new GoogleNewEpisodesFragment();
        } else if (pos == 2) {
            fragment = new GoogleNewEpisodesFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }
}
