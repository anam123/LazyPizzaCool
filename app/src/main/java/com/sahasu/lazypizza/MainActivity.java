package com.sahasu.lazypizza;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView;
    private BottomBar bottomBar;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
        com.sahasu.lazypizza.PrefManager prefManager;
        prefManager = new com.sahasu.lazypizza.PrefManager(this);
        prefManager.setPressedHowToUse(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment frag = new MarketMain();
                if (tabId == R.id.tab_calls) {
//                    Intent market = new Intent(getApplicationContext(),menuFood.class);
//                    startActivity(market);
                    frag = new MarketMain();
                } else if (tabId == R.id.tab_groups) {
                    // The tab with id R.id.tab_groups was selected,
                    // change your content accordingly.
                    frag = new MenuMain();
                } else if (tabId == R.id.tab_chats) {
                    // The tab with id R.id.tab_chats was selected,
                    // change your content accordingly.
                    frag = new Wallet();
                }
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, frag);
                fragmentTransaction.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
                finish();
            }
//            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

//    @Override
//    public void onBackPressed() {
//        if (exit) {
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back
// again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
//
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
            Intent myprofile = new Intent(getApplicationContext(), ProfilePage.class);
            startActivity(myprofile);
        } else if (id == R.id.balance) {
            Intent mybalance = new Intent(getApplicationContext(), MyBalance.class);
            startActivity(mybalance);
        } else if (id == R.id.orders) {
            Intent order = new Intent(getApplicationContext(), MyOrders.class);
            startActivity(order);
        } else if (id == R.id.use) {

            com.sahasu.lazypizza.PrefManager prefManager;
            prefManager = new com.sahasu.lazypizza.PrefManager(this);
            prefManager.setFirstTimeLaunch(true);
            prefManager.setPressedHowToUse(true);

            Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(welcome);
        } else if (id == R.id.logout) {
            GoogleLogin.logout();
            Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}