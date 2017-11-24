package utils;

import com.tivachkov.reservations.reservations.*;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import helpers.DatabaseHandler;

/**
 * Created by tivachkov on 11/19/2017.
 */

public class Utils {

    private static String getJSONStringFromFile(int rawFile, Context context) throws IOException {
        String contentStr = "";

        InputStream is = context.getResources().openRawResource(rawFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
        } catch (Exception e) { e.printStackTrace();
        } finally { reader.close(); }

        contentStr = out.toString();

        return contentStr;
    }

    public static void insertCustomersIntoDB(Context context, DatabaseHandler db){

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.customer_list, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONArray customers = new JSONArray(jsonString);

            for(int i = 0; i < customers.length(); i++){
                Customer customer = new Customer();

                String name = customers.getJSONObject(i).getString("customerFirstName");
                String surname = customers.getJSONObject(i).getString("customerLastName");
                customer.setName(name);
                customer.setSurname(surname);
                customer.setId(customers.getJSONObject(i).getInt("id"));
                db.addReservation(customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void insertTablesIntoDB(Context context, DatabaseHandler db){

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.table_map, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONArray tables = new JSONArray(jsonString);

            for(int i = 0; i < tables.length(); i++){
                Table table = new Table(tables.getBoolean(i));
                db.addTable(table);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
