package com.xencosworks.fittset;

import android.content.ContentValues;
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

import com.xencosworks.fittset.helpers.AllExercisesPageAdapter;


public class AllExercises extends AppCompatActivity implements DrawerLayout.DrawerListener {

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
        ContentValues values;
        switch (item.getItemId()) {
            case R.id.action_allex_settings:
                return true;
            case R.id.action_allex_dummy_chest:
                return true;
            case R.id.action_allex_dummy_shoulders:
                return true;
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

    //    private void customTabSetup(){
    //        tvMuscleDays = findViewById(R.id.all_exercises_tab_muscle_days);
    //        tvDetails = findViewById(R.id.all_exercises_tab_details);
    //
    //        tvMuscleDays.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                viewPager.setCurrentItem(0);
    //            }
    //        });
    //
    //        tvDetails.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                viewPager.setCurrentItem(1);
    //            }
    //        });
    //
    //        int currentItem = viewPager.getCurrentItem();
    //        if (currentItem==0){
    //            customTabSwitchControl(0);
    //        }else if(currentItem==1){
    //            customTabSwitchControl(1);
    //        }else {
    //            customTabSwitchControl(-1);
    //        }
    //
    //        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    //            public void onPageScrollStateChanged(int state) {}
    //            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    //
    //            public void onPageSelected(int position) {
    //                if (position==0){
    //                    customTabSwitchControl(0);
    //                }else if(position==1){
    //                    customTabSwitchControl(1);
    //                }else {
    //                    customTabSwitchControl(-1);
    //                }
    //            }
    //        });
    //
    //
    //    }
    //    private void customTabSwitchControl(int code){
    //        if(code==0){
    //            tvMuscleDays.setTextColor(getResources().getColor(R.color.colorPrimary));
    //            tvMuscleDays.setBackgroundResource(R.drawable.oval_chip_selected);
    //            tvDetails.setTextColor(getResources().getColor(R.color.colorNotSelected));
    //            tvDetails.setBackgroundResource(R.drawable.oval_chip);
    //
    //        }else if(code==1){
    //            tvMuscleDays.setTextColor(getResources().getColor(R.color.colorNotSelected));
    //            tvMuscleDays.setBackgroundResource(R.drawable.oval_chip);
    //            tvDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
    //            tvDetails.setBackgroundResource(R.drawable.oval_chip_selected);
    //        }else{
    //            tvMuscleDays.setTextColor(getResources().getColor(R.color.colorNotSelected));
    //            tvMuscleDays.setBackgroundResource(R.drawable.oval_chip);
    //            tvDetails.setTextColor(getResources().getColor(R.color.colorNotSelected));
    //            tvDetails.setBackgroundResource(R.drawable.oval_chip);
    //        }
    //    }

}
