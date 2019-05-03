package com.vv.PNR_Enquiry_IndianRailways.Model;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 16-03-2018
 * @lastMod_details -> Changes made in order to accomodate the usage of checker framework's annotations
 * @since 04-08-2017
 */

//@SuppressWarnings("initialization.fields.uninitialized")
public class Passenger {
    private int number;
    @NonNull
    private String bookingStatus;
    @NonNull
    private String currentStatus;

    public Passenger() {
        bookingStatus = "";
        currentStatus = "";
        //the above 2 init done in order to deal with the SuppressWarning part
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @NonNull
    public String getBookingStatus() {
        return bookingStatus;
    }

    @EnsuresNonNull({"bookingStatus"})
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @NonNull
    public String getCurrentStatus() {
        return currentStatus;
    }

    @EnsuresNonNull({"currentStatus"})
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /*
    //was not being used
    public Passenger(int number, String bookingStatus, String currentStatus){
        this.number = number;
        this.bookingStatus = bookingStatus;
        this.currentStatus = currentStatus;
    }
    */
}