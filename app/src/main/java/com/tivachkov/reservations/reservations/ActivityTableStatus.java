package com.tivachkov.reservations.reservations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityTableStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_status);

        Intent intent = getIntent();
        String tableNr = intent.getStringExtra("tableNr");
        TextView tvTableNr = (TextView) findViewById(R.id.status_table_nr);
        tvTableNr.setText(tableNr);

        TextView tvOperator = (TextView) findViewById(R.id.status_table_operator);
        tvOperator.setText("John Smith");

        TextView tvAmount = (TextView) findViewById(R.id.status_table_amount);
        tvAmount.setText("12.34 EUR");


    }
}
