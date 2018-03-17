package com.vv.PNR_Enquiry_IndianRailways.Model;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-08-2017
 * @lastMod 17-03-2018
 */

public class Passenger {
    private int number;
    private String bookingStatus;
    private String currentStatus;

    public Passenger() {
        bookingStatus = "";
        currentStatus = "";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    @EnsuresNonNull({"bookingStatus"})
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    @EnsuresNonNull({"currentStatus"})
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
