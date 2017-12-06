package com.watsonlogic.vitalarium.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.user.User;
import com.watsonlogic.vitalarium.presenter.dashboard.DashboardPresenter;
import com.watsonlogic.vitalarium.view.signin.SignInActivity;

import static com.watsonlogic.vitalarium.view.signin.SignInActivity.EXTRA_VITALARIUM_USER;

public class DashboardActivity extends AppCompatActivity
        implements DashboardViewActions, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "DashboardActivity";

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;

    private DashboardPresenter dashboardPresenter;
    private User user;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user = getIntent().getParcelableExtra(EXTRA_VITALARIUM_USER);
        Log.d(TAG, user.toString());

        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.getProject(user.getProjects().get(0));

        getViews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_sign_out:
                signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut(){
        AuthUI.getInstance().signOut(this)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    // user is now signed out
                    startActivity(new Intent(DashboardActivity.this, SignInActivity.class));
                    finish();
                }
            });
    }

    @Override
    public void onGetProjectComplete(Project project) {
        Log.d(TAG, project.toString());
        // store project variables
        this.project = project;
        pagerAdapter.notifyDataSetChanged();
    }

    public class DashboardPagerAdapter extends FragmentStatePagerAdapter {
        public static final String EXTRA_PROJECT = "PROJECT";
        public DashboardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) { //caled when notifyDataSetChanged called
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch(position){
                case 0:
                    if (project != null) {
                        Log.d(TAG, "backlog project not null");
                        fragment = BacklogFragment.newInstance(project.getBacklog());
                    }
                    else {
                        Log.d(TAG, "backlog project is null");
                        fragment = BacklogFragment.newInstance(null);
                    }
                    break;
                case 1: //current sprint
                    if (project != null){
                        Log.d(TAG, "current sprint not null");
                        fragment = SprintFragment.newInstance(project.getSprints().get(0));
                    }
                    else {
                        Log.d(TAG, "current sprint is null");
                        fragment = SprintFragment.newInstance(null);
                    }
                    break;
                default: //next sprint
                    if (project != null) {
                        Log.d(TAG, "next sprint not null");
                        fragment = SprintFragment.newInstance(project.getSprints().get(1));
                    }
                    else {
                        Log.d(TAG, "next sprint is null");
                        fragment = SprintFragment.newInstance(null);
                    }
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return getString(R.string.backlog_page_title);
                case 1:
                    return getString(R.string.this_sprint_page_title);
                case 2:
                    return getString(R.string.next_sprint_page_title);
                default:
                    return null;
            }
        }
    }

    private void getViews(){
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        TextView displayNameTextView = (TextView ) v.findViewById(R.id.nav_header_dashboard_display_name);
        displayNameTextView.setText(user.getDisplayName());
        TextView emailTextView = v.findViewById(R.id.nav_header_dashboard_email);
        emailTextView.setText(user.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
    }
}
