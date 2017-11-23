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

    public static ArrayList<Customer> getArrayListOfCustomers(Context context){
        final ArrayList<Customer> customersList = new ArrayList<>();

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.customer_list, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("=======|", "customers string length = " + jsonString.length() + "-->" + jsonString);
            //JSONObject json = new JSONObject(jsonString.toString());
            JSONArray customers = new JSONArray(jsonString);
            Log.d("=====| ", "JSONArray customers size = " + customers.length());

            // Get Recipe objects from data
            for(int i = 0; i < customers.length(); i++){
                Customer customer = new Customer();

                String name = customers.getJSONObject(i).getString("customerFirstName");
                String surname = customers.getJSONObject(i).getString("customerLastName");
                customer.setName(name);
                customer.setSurname(surname);
                customer.setId(customers.getJSONObject(i).getInt("id"));


                customersList.add(customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customersList;
    }

    public static ArrayList<Table> getArrayListOfTables(Context context){
        final ArrayList<Table> tablesList = new ArrayList<>();

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.table_map, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("=======|", "tables string length = " + jsonString.length() + "-->" + jsonString);
            //JSONObject json = new JSONObject(jsonString.toString());
            JSONArray tables = new JSONArray(jsonString);
            Log.d("=====| ", "JSONArray tables size = " + tables.length());

            // Get Recipe objects from data
            for(int i = 0; i < tables.length(); i++){


//                customers.getJSONObject(i).getString("customerFirstName");

                // debugging
                if (tables.getBoolean(i))
                    Log.d("==============", "Found true in pos=" + i);

                Table table = new Table(tables.getBoolean(i));
//                customer.setFullname(name, surname);
//                customer.setId(tables.getJSONObject(i).getInt("id"));


                tablesList.add(table);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tablesList;
    }

    private static String getJSONStringFromFile(int rawFile, Context context) throws IOException {
        String contentStr = "";

        //InputStream is = context.getResources().openRawResource(R.raw.customer_list);
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

    public static ArrayList<Customer> getArrayListOfCustomersAndInsertIntoDB(Context context, DatabaseHandler db){
        final ArrayList<Customer> customersList = new ArrayList<>();

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.customer_list, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("=======|", "customers string length = " + jsonString.length() + "-->" + jsonString);
            //JSONObject json = new JSONObject(jsonString.toString());
            JSONArray customers = new JSONArray(jsonString);
            Log.d("=====| ", "JSONArray customers size = " + customers.length());

            // Get Recipe objects from data
            for(int i = 0; i < customers.length(); i++){
                Customer customer = new Customer();

                String name = customers.getJSONObject(i).getString("customerFirstName");
                String surname = customers.getJSONObject(i).getString("customerLastName");
                customer.setName(name);
                customer.setSurname(surname);
                customer.setId(customers.getJSONObject(i).getInt("id"));
                customersList.add(customer);

                db.addReservation(customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customersList;
    }

    public static void insertCuatomersIntoDB(Context context, DatabaseHandler db){
        //final ArrayList<Customer> customersList = new ArrayList<>();

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.customer_list, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("=======|", "customers string length = " + jsonString.length() + "-->" + jsonString);
            //JSONObject json = new JSONObject(jsonString.toString());
            JSONArray customers = new JSONArray(jsonString);
            Log.d("=====| ", "JSONArray customers size = " + customers.length());

            // Get Recipe objects from data
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
        //final ArrayList<Table> tablesList = new ArrayList<>();

        try {
            String jsonString = "";
            try {
                jsonString = getJSONStringFromFile(R.raw.table_map, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("=======|", "tables string length = " + jsonString.length() + "-->" + jsonString);
            //JSONObject json = new JSONObject(jsonString.toString());
            JSONArray tables = new JSONArray(jsonString);
            Log.d("=====| ", "JSONArray tables size = " + tables.length());

            // Get Recipe objects from data
            for(int i = 0; i < tables.length(); i++){


//                customers.getJSONObject(i).getString("customerFirstName");

                if (tables.getBoolean(i))
                    Log.d("==============", "Found true in pos=" + i);
                //table.setAvailable(tables.getBoolean(i));
                Table table = new Table(tables.getBoolean(i));
//                customer.setFullname(name, surname);
//                customer.setId(tables.getJSONObject(i).getInt("id"));

                db.addTable(table);
                //tablesList.add(table);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static String readTablesFromFile(Context context) throws IOException {
        String contentStr = "";

        InputStream is = context.getResources().openRawResource(R.raw.table_map);
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

    public static int initializeData() {
        int result = 0;



        return result;
    }

    public static String convertJSONToString(Context context) {
        String JSONstr = "";

        //Log.d("====|","Environment.getDataDirectory() = " + Environment.);
        try {
            InputStream inputStream = context.openFileInput("customer_list.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                JSONstr = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }


        return JSONstr;
    }

}
