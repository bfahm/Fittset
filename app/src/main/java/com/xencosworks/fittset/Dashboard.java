package com.xencosworks.fittset;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.scalified.fab.ActionButton;

public class Dashboard extends AppCompatActivity implements DrawerLayout.DrawerListener {

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("Dashboard");

        setUpNavDrawer();
        setUpFab();
        setUpButtons();

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    private void setUpNavDrawer() {
        //define custom toolbar
        Toolbar mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_nav, R.string.close_nav);

        mDrawerLayout.addDrawerListener(mToggle);
        mDrawerLayout.addDrawerListener(this);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpFab() {
        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button_dashboard);
        actionButton.setButtonColor(getResources().getColor(R.color.colorPrimary));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.colorPrimaryDark));
        actionButton.setImageResource(R.drawable.fab_plus_icon);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, InputForm.class);
                startActivity(intent);
            }
        });
    }

    private void setUpButtons() {
        View routinesButton = findViewById(R.id.dashboard_routines);
        routinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, AllExercises.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        fixNavBar(true);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        fixNavBar(false);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    private void fixNavBar(boolean opened){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            if(opened){
                Window window = getWindow();

                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                // finally change the color to any color with transparency

                window.setStatusBarColor(getResources().getColor(R.color.colorDashboardTrans));

                View decor = getWindow().getDecorView();
                decor.setSystemUiVisibility(0);
            }else {
                Window window = getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.colorDashboardDark));
                View decor = getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
