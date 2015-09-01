package com.winsontan520.hackernews;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;
import com.winsontan520.hackernews.data.HackerNewsItem;
import com.winsontan520.hackernews.event.OpenUrlEvent;
import com.winsontan520.hackernews.event.TopStoriesItemEvent;
import com.winsontan520.hackernews.fragment.NewsItemFragment;
import com.winsontan520.hackernews.fragment.TopStoriesFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static Bus BUS;

    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.nvView) NavigationView nvDrawer;
    ActionBarDrawerToggle mDrawerToggle;

    FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            syncActionBarArrowState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(toolbar);
        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        // drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                //syncActionBarArrowState();
            }

            public void onDrawerOpened(View drawerView) {
                mDrawerToggle.setDrawerIndicatorEnabled(true);
            }
        };

        mDrawer.setDrawerListener(mDrawerToggle);
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);

        // Otto
        BUS = new Bus(ThreadEnforcer.MAIN);
        BUS.register(this);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TopStoriesFragment topStoriesFragment = new TopStoriesFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, topStoriesFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Subscribe
    public void topStoriesItemClicked(TopStoriesItemEvent event) {
        HackerNewsItem item = event.getItem();
        if (item == null) {
            Log.d(TAG, "topStoriesItemClicked item is NULL");
            return;
        }
        Log.d(TAG, "topStoriesItemClicked item = " + item.getId());
        Log.d(TAG, "Title" + item.getTitle());
        Log.d(TAG, "Text" + item.getText());


        NewsItemFragment f = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putInt(NewsItemFragment.ARG_ID, item.getId());
        args.putString(NewsItemFragment.ARG_TTILE, item.getTitle());
        args.putIntegerArrayList(NewsItemFragment.ARG_KIDS, item.getKids());
        f.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, f);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Subscribe
    public void openUrlEvent(OpenUrlEvent event) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getUrl()));
        startActivity(browserIntent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        switch (item.getItemId()) {
            case android.R.id.home:
                if (backStackEntryCount == 0) {
                    if (mDrawerToggle.onOptionsItemSelected(item)) {
                        return true;
                    }
                }else{
                    if (getSupportFragmentManager().popBackStackImmediate()) {
                        return true;
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
//        if (mDrawerToggle.isDrawerIndicatorEnabled() &&
//                mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        } else if (item.getItemId() == android.R.id.home &&
//                getSupportFragmentManager().popBackStackImmediate()) {
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }

    private void syncActionBarArrowState() {
        int backStackEntryCount =
                getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount == 0) {
            animateDrawerToggle(1, 0);
        } else {
            animateDrawerToggle(0, 1);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateDrawerToggle(float start, float end) {
        // 0=closed , 1=open
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }

        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                mDrawerToggle.onDrawerSlide(mDrawer, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        // You can change this duration to more closely match that of the default animation.
        anim.setDuration(500);
        anim.start();
    }

}
