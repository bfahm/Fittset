package com.xencosworks.fittset;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.xencosworks.fittset.Room.Exercise;
import com.xencosworks.fittset.Room.ExerciseViewModel;
import com.xencosworks.fittset.helpers.AllExercisesPageAdapter;
import com.xencosworks.fittset.MuscleDaysFragment.OnButtonClickListener;


public class AllExercises extends AppCompatActivity implements DrawerLayout.DrawerListener, OnButtonClickListener {

    private static String LOGTAG = AllExercises.class.getSimpleName();
    private ViewPager viewPager;
    private int[] icResId = {R.drawable.ic_allex_tab_11, R.drawable.ic_allex_tab_22};
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercises);
        setUpNav();

        viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        AllExercisesPageAdapter adapter = new AllExercisesPageAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < icResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(icResId[i]);
        }

        final int colorSelected = ResourcesCompat.getColor(getResources(), R.color.colorDashboardPrimary, null);
        final int colorNotSelected = ResourcesCompat.getColor(getResources(), R.color.colorTabUnselected, null);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(colorSelected, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(colorNotSelected, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpNav(){
        Toolbar toolbar = findViewById(R.id.toolbar_allex);
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout_allex);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_nav, R.string.close_nav);

        mDrawerLayout.addDrawerListener(mToggle);
        mDrawerLayout.addDrawerListener(this);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ExerciseViewModel exerciseViewModel;
        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        Exercise dummyChest = new Exercise("dummyChest", 1, 10, 10);
        Exercise dummyShoulder = new Exercise("dummyShoulder", 2, 10, 10);
        switch (item.getItemId()) {
            case R.id.action_allex_settings:
                return true;
            case R.id.action_allex_dummy_chest:
                exerciseViewModel.insert(dummyChest);
                return true;
            case R.id.action_allex_dummy_shoulders:
                exerciseViewModel.insert(dummyShoulder);
                return true;
            case R.id.action_allex_delete_all:
                exerciseViewModel.deleteAllExercises();
                Toast.makeText(this, "All Exercises Deleted", Toast.LENGTH_LONG).show();
            default:
                return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_exercises, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem()==1){
            viewPager.setCurrentItem(0);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        fixNavBar(true);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        fixNavBar(false);
    }

    @Override
    public void onDrawerStateChanged(int newState) {}

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
            }else {
                Window window = getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }
    }

    //Overriding method of the mOnButtonClicked Interface of child MuscleDaysFragment
    @Override
    public void onButtonClicked(View view, int code) {
        //Assignment of variables living in the child fragment
        DetailsFragment.idFromParentPage = code;

        int currPos=viewPager.getCurrentItem();
        viewPager.setCurrentItem(currPos+1);
    }
}
