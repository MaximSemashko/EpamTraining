package com.example.epamtraining;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GooglePodcastsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private GoogleFragmentPagerAdapter googleFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_podcasts);

        viewPager = findViewById(R.id.viewpager);
        googleFragmentPagerAdapter = new GoogleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(googleFragmentPagerAdapter);
    }
}
