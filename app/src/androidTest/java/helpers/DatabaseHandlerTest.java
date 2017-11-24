package helpers;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.tivachkov.reservations.reservations.Alarm;
import com.tivachkov.reservations.reservations.Customer;
import com.tivachkov.reservations.reservations.MainActivity;
import com.tivachkov.reservations.reservations.Table;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.*;
import org.hamcrest.core.IsCollectionContaining;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Created by tivachkov on 11/23/2017.
 */


@RunWith(AndroidJUnit4.class)
public class DatabaseHandlerTest {

    private DatabaseHandler database;
    private String DATABASE_NAME = "QuandooReservations";

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(DATABASE_NAME);
        database = new DatabaseHandler(getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void isTableExisting() throws Exception {
        boolean exists = database.isTableExisting("Alarms");
        assertThat(exists, is(true));

        database.dropAlarmsTableIfExists(database.getWritableDatabase());
        exists = database.isTableExisting("Alarms");
        assertThat(exists, is(false));

        database.createAlarmsTable(database.getWritableDatabase());
        exists = database.isTableExisting("Alarms");
        assertThat(exists, is(true));

        //--------------------------------------------------------------

        exists = database.isTableExisting("Reservations");
        assertThat(exists, is(true));

        database.dropReservationsTableIfExists(database.getWritableDatabase());
        exists = database.isTableExisting("Reservations");
        assertThat(exists, is(false));

        database.createReservationsTable(database.getWritableDatabase());
        exists = database.isTableExisting("Reservations");
        assertThat(exists, is(true));

        //--------------------------------------------------------------

        exists = database.isTableExisting("Tables");
        assertThat(exists, is(true));

        database.dropTablesTableIfExists(database.getWritableDatabase());
        exists = database.isTableExisting("Tables");
        assertThat(exists, is(false));

        database.createTablesTable(database.getWritableDatabase());
        exists = database.isTableExisting("Tables");
        assertThat(exists, is(true));
    }

    @Test
    public void createReservationsTable() throws Exception {
        database.dropReservationsTableIfExists(database.getWritableDatabase());
        database.createReservationsTable(database.getWritableDatabase());
        boolean expected = database.isTableExisting("Reservations");
        assertThat(expected, is(true));
    }

    @Test
    public void dropReservationsTableIfExists() throws Exception {
        database.dropReservationsTableIfExists(database.getWritableDatabase());
        boolean expected = database.isTableExisting("Reservations");
        assertThat(expected, is(false));
        database.createReservationsTable(database.getWritableDatabase());
    }

    @Test
    public void addReservation() throws Exception {
        database.addReservation(new Customer("Abraham", "Lincoln", 1));
        ArrayList<Customer> reservationsList = database.getAllReservations();

        assertThat(reservationsList, hasSize(1));
        assertThat(reservationsList.get(0).getName(), is("Abraham"));
        assertThat(reservationsList.get(0).getSurname(), is("Lincoln"));
        assertThat(reservationsList.get(0).getId(), is(1));

        //--------------------------------------------------------------

        database.addReservation(new Customer("Bill", "Gates", 7));
        reservationsList = database.getAllReservations();

        assertThat(reservationsList, hasSize(2));
        assertThat(reservationsList.get(1).getName(), is("Bill"));
        assertThat(reservationsList.get(1).getSurname(), is("Gates"));
        assertThat(reservationsList.get(1).getId(), is(7));

    }

    @Test
    public void deleteReservation() throws Exception {
        database.deleteReservation(7);

        ArrayList<Customer> reservationsList = database.getAllReservations();
        boolean id7Exists = false;
        for (Customer c : reservationsList) {
            if (c.getId() == 7)
                id7Exists = true;
        }

        assertThat(id7Exists, is(false));

    }


    @Test
    public void deleteAllReservations() throws Exception {
        database.deleteAllReservations();
        ArrayList<Customer> reservationsList = database.getAllReservations();

        assertThat(reservationsList, hasSize(0));
    }

    @Test
    public void deleteAllReservations1() throws Exception {
        database.deleteAllReservations();
        ArrayList<Customer> reservationsList = database.getAllReservations();

        assertThat(reservationsList, empty());

        Alarm alarm = database.getAlarm(MainActivity.ALARM_ID);
        assertThat(alarm, is(nullValue()));

    }

    @Test
    public void getAllReservations() throws Exception {
        database.deleteAllReservations();

        ArrayList<Customer> reservationsList = database.getAllReservations();

        assertThat(reservationsList, empty());

        database.addReservation(new Customer("Abraham", "Lincoln", 1));
        database.addReservation(new Customer("Bill", "Gates", 7));
        database.addReservation(new Customer("Charles", "de Gaule", 11));
        database.addReservation(new Customer("Nelson", "Mandela", 5));

        reservationsList = database.getAllReservations();

        assertThat(reservationsList, hasSize(4));

        database.deleteAllReservations();
    }

    @Test
    public void createTablesTable() throws Exception {
        database.dropTablesTableIfExists(database.getWritableDatabase());
        database.createTablesTable(database.getWritableDatabase());
        boolean expected = database.isTableExisting("Tables");
        assertThat(expected, is(true));

    }

    @Test
    public void getTables() throws Exception {

        database.addTable(new Table(true));
        database.addTable(new Table(false));
        database.addTable(new Table(false));

        database.addTable(new Table(true));
        database.addTable(new Table(true));
        database.addTable(new Table(false));
        database.addTable(new Table(true));

        ArrayList<Table> tablesList = database.getTables();

        assertThat(tablesList, hasSize(7));

    }

    @Test
    public void addTable() throws Exception {

        database.addTable(new Table(false));
        ArrayList<Table> tablesList = database.getTables();

        assertThat(tablesList, hasSize(1));
        assertThat(tablesList.get(0).isAvailable(), is(false));

        database.addTable(new Table(true));
        tablesList = database.getTables();

        assertThat(tablesList, hasSize(2));
        assertThat(tablesList.get(1).isAvailable(), is(true));

        database.deleteAllTables();
    }

    @Test
    public void setTableAvailability() throws Exception {
        database.dropTablesTableIfExists(database.getWritableDatabase());
        database.createTablesTable(database.getWritableDatabase());
        database.addTable(new Table(true));
        ArrayList<Table> tablesList = database.getTables();

        assertThat(tablesList, hasSize(1));

        //I pass 0 instead of 1, because in the implementation of the method the id is incremented by 1
        database.setTableAvailability(true, 0);
        Table table = database.getTables().get(0);

        assertThat(table.isAvailable(), is(true));

        database.setTableAvailability(false, 0);
        table = database.getTables().get(0);

        assertThat(table.isAvailable(), is(false));
    }

    @Test
    public void deleteAllTables() throws Exception {
        database.dropTablesTableIfExists(database.getWritableDatabase());
        database.createTablesTable(database.getWritableDatabase());

        database.addTable(new Table(false));
        database.addTable(new Table(true));
        database.addTable(new Table(true));
        database.deleteAllTables();

        ArrayList<Table> tablesList = database.getTables();
        assertThat(tablesList, hasSize(0));
    }

    @Test
    public void dropTablesTableIfExists() throws Exception {
        database.dropTablesTableIfExists(database.getWritableDatabase());

        assertThat(database.isTableExisting("Tables"), is(false));
    }

    @Test
    public void createAlarmsTable() throws Exception {
        database.dropAlarmsTableIfExists(database.getWritableDatabase());
        database.createAlarmsTable(database.getWritableDatabase());

        assertThat(database.isTableExisting("Alarms"), is(true));

    }

    @Test
    public void dropAlarmsTableIfExists() throws Exception {
        database.createAlarmsTable(database.getWritableDatabase());
        database.dropAlarmsTableIfExists(database.getWritableDatabase());

        assertThat(database.isTableExisting("Alarms"), is(false));

        database.createAlarmsTable(database.getWritableDatabase());
    }

    @Test
    public void addAlarm() throws Exception {
        database.dropAlarmsTableIfExists(database.getWritableDatabase());
        database.createAlarmsTable(database.getWritableDatabase());
        database.addAlarm(2345, 123456789);
        Alarm alarm = database.getAlarm(2345);

        assertThat(alarm, not(nullValue()));
    }

    @Test
    public void updateAlarm() throws Exception {
        database.dropAlarmsTableIfExists(database.getWritableDatabase());
        database.createAlarmsTable(database.getWritableDatabase());
        database.addAlarm(2345, 1010101010);
        database.updateAlarm(2345, 123456789);

        long initialSchedule = 1010101010;
        long updatedSchedule = 123456789;
        assertThat(database.getAlarm(2345).getSchedule(), is(updatedSchedule));
        assertThat(database.getAlarm(2345).getSchedule(), not(initialSchedule));
    }

    @Test
    public void deleteAlarm() throws Exception {
        database.dropAlarmsTableIfExists(database.getWritableDatabase());
        database.createAlarmsTable(database.getWritableDatabase());
        database.addAlarm(234, 23456789);
        database.addAlarm(345, 3456789);
        database.deleteAlarm(234);

        Alarm alarm = database.getAlarm(234);

        assertThat(alarm, nullValue());

        long expectedSchedule = 3456789;

        assertThat(database.getAlarm(345), not(nullValue()));
        assertThat(database.getAlarm(345).getSchedule(), is(expectedSchedule));
    }

    @Test
    public void getAlarm() throws Exception {
        database.dropAlarmsTableIfExists(database.getWritableDatabase());
        database.createAlarmsTable(database.getWritableDatabase());
        database.addAlarm(123, 123456789);
        database.addAlarm(234,23456789);
        database.addAlarm(789, 987654321);

        Alarm alarm = database.getAlarm(123);
        long schedule = 123456789;

        assertThat(alarm, not(nullValue()));
        assertThat(alarm.getSchedule(), is(schedule));
        assertThat(alarm.getId(), is(123));
    }

}