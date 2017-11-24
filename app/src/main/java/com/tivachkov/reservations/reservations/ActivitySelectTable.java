package com.tivachkov.reservations.reservations;

import android.app.Activity;
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

    private TablesAdapter mTablesAdapter;
    private DatabaseHandler mDBHandler;
    public static Activity sThisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);

        sThisActivity = this;

        GridView gridView = (GridView)findViewById(R.id.grid_view_tables);

        mDBHandler = new DatabaseHandler(this);
        final ArrayList<Table> tablesList = mDBHandler.getTables();

        mTablesAdapter = new TablesAdapter(this, tablesList);
        gridView.setAdapter(mTablesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Table currentTable = tablesList.get(position);
                if (currentTable.isAvailable()) {
                    currentTable.setAvailable(false);
                    mDBHandler.setTableAvailability(currentTable.isAvailable(), position);
                    mTablesAdapter.notifyDataSetChanged();

                    Intent intent = getIntent();
                    int customerID = intent.getIntExtra("customerId", 0);
                    mDBHandler.deleteReservation(customerID);

                    MainActivity.sThisActivity.finish();
                    Intent mainActivityIntent = new Intent(sThisActivity, MainActivity.class);
                    startActivity(mainActivityIntent);
                } else {
                    //Here you can put some code to open another activity to check the status of the table.
                    //At the end, if a table is available, there is not status to show.
                    //But an occupied table has a status to show.
                }


            }
        });

    }

}
