package com.vv.PNR_Enquiry_IndianRailways.Model;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.EMPTY_STR;
import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.NA_INT;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-05-2019
 * @since 04-08-2017
 */

public class Passenger {

    private int number = NA_INT;
    @NonNull
    private String bookingStatus = EMPTY_STR;
    @NonNull
    private String currentStatus = EMPTY_STR;

    public Passenger() {
    }

    public Passenger(int number, @NonNull String bookingStatus, @NonNull String currentStatus) {
        this.number = number;
        this.bookingStatus = bookingStatus;
        this.currentStatus = currentStatus;
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

    @Override
    public String toString() {
        return String.format("Passenger : %d %s/%s", number, bookingStatus, currentStatus);
    }
}