package com.tivachkov.reservations.reservations;

/**
 * Created by E5430 on 11/20/2017.
 */

public class Table {

    private boolean mAvailable;

    public Table(boolean available) {
        this.mAvailable = available;
    }

    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean available) {
        this.mAvailable = available;
    }
}
