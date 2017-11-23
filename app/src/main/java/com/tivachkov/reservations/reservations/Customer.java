package com.tivachkov.reservations.reservations;

/**
 * Created by tivachkov on 11/20/2017.
 */

public class Customer {

    private String mName, mSurname;
    private int mID;

    public Customer() {

    }

    public Customer (String name, String surname, int id) {
        this.mName = name;
        this.mSurname = surname;
        this.mID = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        this.mSurname = surname;
    }

    public int getId() {
        return mID;
    }

    public void setId(int id) {
        this.mID = id;
    }



}
