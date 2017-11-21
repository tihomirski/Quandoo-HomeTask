package com.tivachkov.reservations.reservations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import adapters.TablesAdapter;
import helpers.DatabaseHandler;

public class ActivitySelectTable extends AppCompatActivity {

    private TablesAdapter tablesAdapter;
    private DatabaseHandler dbHandler;
    public static Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);

        thisActivity = this;

        GridView gridView = (GridView)findViewById(R.id.grid_view_tables);

        dbHandler = new DatabaseHandler(this);
        final ArrayList<Table> tablesList = dbHandler.getTables();

        tablesAdapter = new TablesAdapter(this, tablesList);
        gridView.setAdapter(tablesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Table currentTable = tablesList.get(position);
                if (currentTable.isAvailable()) {
                    currentTable.setAvailable(false);
                    dbHandler.setTableAvailability(currentTable.isAvailable(), position);
                    // This tells the GridView to redraw itself
                    // in turn calling your BooksAdapter's getView method again for each cell
                    tablesAdapter.notifyDataSetChanged();

                    Intent intent = getIntent();
                    //int customerID = Integer.parseInt(intent.getStringExtra("customerId"));
                    int customerID = intent.getIntExtra("customerId", 0);
                    dbHandler.deleteReservation(customerID);

                    MainActivity.thisActivity.finish();
                    Intent mainActivityIntent = new Intent(thisActivity, MainActivity.class);
                    startActivity(mainActivityIntent);
                } else {
                    //Here you can put some code to open another activity to check the status of the table
                }


            }
        });

    }

}
