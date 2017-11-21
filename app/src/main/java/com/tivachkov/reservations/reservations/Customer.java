package com.tivachkov.reservations.reservations;

/**
 * Created by tivachkov on 11/20/2017.
 */

public class Customer {

    private String name, surname;
    private int id;

    public Customer() {

    }

    public Customer (String name, String surname, int id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
