package com.tivachkov.reservations.reservations;

import fragments.*;
import helpers.DatabaseHandler;
import utils.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;



public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private String customersJSON, tablesJSON;
    private DatabaseHandler dbHandler;
    public static Activity thisActivity;
    private static final int ALARM_ID = 2057;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = this;
        if (ActivitySelectTable.thisActivity != null)
            ActivitySelectTable.thisActivity.finish();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBottomNavigation();
        //-------------------------| put "loading. please stand by" |----------
        dbHandler = new DatabaseHandler(this);
        if (dbHandler.getDBTableCount("Tables") < 0)
            initializeDB();
        //---------------------------------------------------------------------

        if (savedInstanceState == null) {

            loadHomeFragment();
        }

        Calendar c = GregorianCalendar.getInstance();
        long millis = c.getTimeInMillis();
        millis += 1000*60*15;
        Intent wakeIntent = new Intent(this, broadcastReceivers.RemoveReservations.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_ID, wakeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);


    }

    private void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        loadHomeFragment();
                        return true;
                    case R.id.action_customers:
                        loadCustomersFragment();
                        return true;
                    case R.id.action_tables:
                        loadTablesFragment();
                        return true;
                }
                return false;
            }
        });
    }

    private void loadHomeFragment() {

        HomeFragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadCustomersFragment() {

        CustomersFragment fragment = CustomersFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadTablesFragment() {

        TablesFragment fragment = TablesFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void initializeDB() {
        //SQLiteDatabase db = dbHandler.getWritableDatabase();
        Utils.insertCuatomersIntoDB(this, dbHandler);
        Utils.insertTablesIntoDB(this, dbHandler);
    }


    public void buttonResetDB(View view) {
        dbHandler.deleteAllReservations();
        dbHandler.deleteAllTables();
        initializeDB();
    }

}
