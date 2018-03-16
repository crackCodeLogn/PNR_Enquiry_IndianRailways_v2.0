package com.vv.PNR_Enquiry_IndianRailways.Model;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-08-2017
 * @lastMod 16-03-2018
 */

public class Passenger {
    private int number;
    private String bookingStatus;
    private String currentStatus;

    public Passenger() {

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

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

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
