package com.tivachkov.reservations.reservations;

/**
 * Created by tivachkov on 11/22/2017.
 */

public class Alarm {
    private int mID;
    private long mSchedule;

    public Alarm(int id, long schedule) {
        this.mID = id;
        this.mSchedule = schedule;
    }

    public int getId() {
        return mID;
    }

    public void setId(int id) {
        this.mID = id;
    }

    public long getSchedule() {
        return mSchedule;
    }

    public void setSchedule(long schedule) {
        this.mSchedule = schedule;
    }
}
