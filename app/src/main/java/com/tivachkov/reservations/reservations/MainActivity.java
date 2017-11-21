package com.tivachkov.reservations.reservations;

import fragments.*;
import helpers.DatabaseHandler;
import utils.Utils;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

//import static utils.Utils.getArrayListOfCustomersAndInsertIntoDB;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private String customersJSON, tablesJSON;
    private DatabaseHandler dbHandler;
    public static Activity thisActivity;

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
//        dbHandler.deleteAllReservations();
//        dbHandler.deleteAllTables();
        if (dbHandler.getDBTableCount("Tables") < 0 && dbHandler.getDBTableCount("Reservations") < 0)
            initializeDB();
        //---------------------------------------------------------------------

        if (savedInstanceState == null) {

            loadHomeFragment();
        }

        //Utils.convertJSONToString(this);
        ///////initializeData();
        //Log.d("=======|", "-------------------------| Customers |---------------\n" + customersJSON);
        //Log.d("=======|", "-------------------------| Tables |---------------\n" + tablesJSON);


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

//    private void initializeData() {
//
//        //The case, initially I load the lists from the read-only files and the update and removal of tables happen over the list adapted to the ListView of the customers.
//        //int result = 0;
//
//        try {
//            customersJSON = Utils.readCustomersFromFile();
//            tablesJSON = readTablesFromFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //return result;
//    }

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
