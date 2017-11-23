package com.tivachkov.reservations.reservations;

import fragments.*;
import helpers.DatabaseHandler;
import utils.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;



public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private DatabaseHandler mDBHandler;
    public static Activity sThisActivity;
    private static final int ALARM_ID = 1234;
    ProgressDialog mRingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sThisActivity = this;
        if (ActivitySelectTable.sThisActivity != null)
            ActivitySelectTable.sThisActivity.finish();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBottomNavigation();

        mRingProgressDialog = new ProgressDialog(this);

        mDBHandler = new DatabaseHandler(this);

//        mDBHandler.dropAlarmsTableIfExists(mDBHandler.getWritableDatabase());
//        mDBHandler.dropReservationsTableIfExists(mDBHandler.getWritableDatabase());
//        mDBHandler.dropTablesTableIfExists(mDBHandler.getWritableDatabase());

        if (!mDBHandler.isTableExisting("Tables")) {
            Log.e("HHHHHHHHHHHHH","Initializing all DBs");

            mRingProgressDialog.setMessage("App is initializing ....\n\n");
            mRingProgressDialog.setTitle("Please wait");
            mRingProgressDialog.setCanceledOnTouchOutside(false);
            mRingProgressDialog.setCancelable(true);
            mRingProgressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Do stuff
                        initializeDB();
                        initializeAlarms();

                        Alarm alarm = mDBHandler.getAlarm(ALARM_ID);
                        long now = GregorianCalendar.getInstance().getTimeInMillis();
                        if (alarm == null) {
                            createAlarm();
                        } else if (alarm.getSchedule() < now) {
                            updateAlarm();
                        } else
                            Log.e("0101010101010101", "Alarm should NOT be created.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mRingProgressDialog.dismiss();
                }
            }).start();
        } else {
            Alarm alarm = mDBHandler.getAlarm(ALARM_ID);
            long now = GregorianCalendar.getInstance().getTimeInMillis();
            if (alarm == null) {
                createAlarm();
            } else if (alarm.getSchedule() < now) {
                updateAlarm();
            } else
                Log.e("0101010101010101", "Alarm should NOT be created.");
        }

        if (savedInstanceState == null) {
            loadHomeFragment();
        }


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
        mDBHandler.deleteAllReservations();
        mDBHandler.deleteAllTables();

        Utils.insertCuatomersIntoDB(sThisActivity, mDBHandler);
        Utils.insertTablesIntoDB(sThisActivity, mDBHandler);
    }

    private void initializeAlarms() {

        mDBHandler.dropAlarmsTableIfExists(mDBHandler.getWritableDatabase());
        mDBHandler.createAlarmsTable(mDBHandler.getWritableDatabase());

    }

    public void buttonResetDB(View view) {

        mRingProgressDialog.setMessage("App is initializing ....\n\n");
        mRingProgressDialog.setTitle("Please wait");
        mRingProgressDialog.setCanceledOnTouchOutside(false);
        mRingProgressDialog.setCancelable(true);
        mRingProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Do stuff
                    mDBHandler.deleteAllReservations();
                    mDBHandler.deleteAllTables();

                    initializeDB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mRingProgressDialog.dismiss();
            }
        }).start();






    }

    public void buttonResetAlarmsDB(View view) {

        mRingProgressDialog.setMessage("App is initializing ....\n\n");
        mRingProgressDialog.setTitle("Please wait");
        mRingProgressDialog.setCanceledOnTouchOutside(false);
        mRingProgressDialog.setCancelable(true);
        mRingProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Do stuff
                    initializeAlarms();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mRingProgressDialog.dismiss();
            }
        }).start();





    }

    private void createAlarm() {
        Log.e("XxXxXxXxXxXxXxXxXxXx", "Setting an ALARM.......");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar c = GregorianCalendar.getInstance();
        long millis = c.getTimeInMillis();
        millis += 1000 * 60 * 2;

        Intent wakeIntent = new Intent(this, broadcastReceivers.RemoveReservations.class);
        wakeIntent.putExtra("alarmID", ALARM_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_ID, wakeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        mDBHandler.addAlarm(ALARM_ID, millis);

        if(millis == mDBHandler.getAlarm(ALARM_ID).getSchedule()) {
            Log.e("010101010101010", "millis == DB");
        } else
            Log.e("010101010101010", "millis != DB");

    }

    private void updateAlarm() {
        Log.e("XxXxXxXxXxXxXxXxXxXx", "Updating an ALARM.......");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar c = GregorianCalendar.getInstance();
        long millis = c.getTimeInMillis();
        millis += 1000 * 60 * 2;

        Intent wakeIntent = new Intent(this, broadcastReceivers.RemoveReservations.class);
        wakeIntent.putExtra("alarmID", ALARM_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_ID, wakeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        mDBHandler.updateAlarm(ALARM_ID, millis);

        if(millis == mDBHandler.getAlarm(ALARM_ID).getSchedule()) {
            Log.e("010101010101010", "millis == DB");
        } else
            Log.e("010101010101010", "millis != DB");
    }

}
