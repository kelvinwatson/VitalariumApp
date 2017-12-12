package com.watsonlogic.vitalarium.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.VitalariumConstants.TaskAction;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.user.User;
import com.watsonlogic.vitalarium.presenter.dashboard.DashboardPresenter;
import com.watsonlogic.vitalarium.view.image.CircularTransform;
import com.watsonlogic.vitalarium.view.signin.SignInActivity;
import com.watsonlogic.vitalarium.view.taskdetail.TaskDetailActivity;

import java.util.ArrayList;

import static com.watsonlogic.vitalarium.VitalariumConstants.SNACKBAR_DELAY;
import static com.watsonlogic.vitalarium.view.signin.SignInActivity.EXTRA_VITALARIUM_USER;

public class DashboardActivity extends AppCompatActivity
        implements DashboardViewActions, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "DashboardActivity";
//    public static final String EXTRA_USER = "USER";
    public static final String EXTRA_TASK = "TASK";
    public static final String EXTRA_TASK_ACTION = "TASK_ACTION";
    public static final String EXTRA_PROJECT_ID = "PROJECT_ID";
    //    public static final String EXTRA_UPDATE_STATUS = "TASK_UPDATE_STATUS";
    public static final String EXTRA_SPRINTS = "SPRINTS";
    public static final int RC_ADD = 90;
    public static final int RC_UPDATE = 91;
    private static final int RC_DELETE = 92;

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
        switch (item.getItemId()) {
            case R.id.action_refresh:
                ((SwipeRefreshLayout) findViewById(R.id.swipe_refresh)).setRefreshing(true);
                dashboardPresenter.getProject(project.getId());
                return true;
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
        switch (item.getItemId()) {
            case R.id.nav_add_task:
                dashboardPresenter.onClickAddTask();
        }

        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
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

    @Override
    public void onClickAddTask(){
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(EXTRA_VITALARIUM_USER, user);
        intent.putExtra(EXTRA_TASK_ACTION, TaskAction.ADD.name());

        intent.putExtra(EXTRA_PROJECT_ID, project.getId());
        startActivityForResult(intent, RC_ADD);
    }

    @Override
    public void onClickUpdateTask(com.watsonlogic.vitalarium.model.task.Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(EXTRA_VITALARIUM_USER, user);
        intent.putExtra(EXTRA_TASK_ACTION, TaskAction.UPDATE.name());

        intent.putParcelableArrayListExtra(EXTRA_SPRINTS, (ArrayList<? extends Parcelable>) project.getSprints());
        intent.putExtra(EXTRA_TASK, task);
        startActivityForResult(intent, RC_UPDATE);
    }

    @Override
    public void onClickDeleteUpdateTask(com.watsonlogic.vitalarium.model.task.Task task) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        Log.d(TAG, String.valueOf(requestCode));
        Log.d(TAG, String.valueOf(resultCode));

        switch (requestCode) {
            case RC_UPDATE:
                switch (resultCode) {
                    case 200:
                        showSnackbar(getString(R.string.task_update_success), (com.watsonlogic.vitalarium.model.task.Task) (data.getParcelableExtra(EXTRA_TASK)));
                }
        }

        super.onActivityResult(requestCode, resultCode, data);

        dashboardPresenter.getProject(user.getProjects().get(0));
    }

    /**
     * Shows snackbar after a slight delay
     *
     * @param message
     */
    private void showSnackbar(final String message, final com.watsonlogic.vitalarium.model.task.Task task) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(findViewById(R.id.task_recycler), message, Snackbar.LENGTH_LONG)
                        .setActionTextColor(ContextCompat.getColor(DashboardActivity.this, R.color.colorTertiary))
                        .setAction("View", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Nothing for now
                                dashboardPresenter.onClickUpdateTask(task);
                            }
                        })
                        .show();
            }
        }, SNACKBAR_DELAY);
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
            switch (position) {
                case 0:
                    if (project != null) {
                        Log.d(TAG, "backlog project not null");
                        fragment = BacklogFragment.newInstance(project.getBacklog(), project.getId());
                    } else {
                        Log.d(TAG, "backlog project is null");
                        fragment = BacklogFragment.newInstance(null, null);
                    }
                    break;
                case 1: //current sprint
                    if (project != null) {
                        Log.d(TAG, "current sprint not null");
                        fragment = SprintFragment.newInstance(project.getSprints().get(0), project.getId());
                    } else {
                        Log.d(TAG, "current sprint is null");
                        fragment = SprintFragment.newInstance(null, null);
                    }
                    break;
                default: //next sprint
                    if (project != null) {
                        Log.d(TAG, "next sprint not null");
                        fragment = SprintFragment.newInstance(project.getSprints().get(1), project.getId());
                    } else {
                        Log.d(TAG, "next sprint is null");
                        fragment = SprintFragment.newInstance(null, null);
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
            switch (position) {
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

    private void getViews() {
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.add_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardPresenter.onClickAddTask();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        ImageView userPhotoImageView = headerView.findViewById(R.id.user_photo);
        Picasso.with(this).load(user.getPhotoUrl())
                .transform(new CircularTransform())
                .into(userPhotoImageView);
        TextView displayNameTextView = headerView.findViewById(R.id.nav_header_dashboard_display_name);
        displayNameTextView.setText(user.getDisplayName());
        TextView emailTextView = headerView.findViewById(R.id.nav_header_dashboard_email);
        emailTextView.setText(user.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
    }

}
