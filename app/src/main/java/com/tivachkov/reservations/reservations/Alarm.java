package com.tivachkov.reservations.reservations;

/**
 * Created by tivachkov on 11/22/2017.
 */

public class Alarm {
    int id;
    long schedule;

    public Alarm(int id, long schedule) {
        this.id = id;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSchedule() {
        return schedule;
    }

    public void setSchedule(long schedule) {
        this.schedule = schedule;
    }
}
