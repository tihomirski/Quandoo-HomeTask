package com.tivachkov.reservations.reservations;

/**
 * Created by E5430 on 11/20/2017.
 */

public class Table {

    private boolean available;

    public Table(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
