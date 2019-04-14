package com.example.epamtraining;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initDrawer();

    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    public void initDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        final HeaderView headerView = navigationView
                .getHeaderView(0)
                .findViewById(R.id.header_view);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = generateGolor();
                headerView.updateImage(color);
                Log.i(TAG, "color: " + color);
            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        selectDrawerItem(menuItem);

                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment;
        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.first_fragment_item:
                fragmentClass = FirstFragment.class;

                break;
            case R.id.second_fragment_item:
                fragmentClass = SecondFragment.class;

                break;
            default:
                fragmentClass = FirstFragment.class;

                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
        } catch (Exception e) {
            Log.e(TAG, "selectDrawerItem: " + e);
        }
    }

    private String generateGolor() {
        Random random = new Random();
        int colorCode = random.nextInt(0xffffff + 1);
        String color = String.format("#%06x", colorCode);

        return color;
    }
}
