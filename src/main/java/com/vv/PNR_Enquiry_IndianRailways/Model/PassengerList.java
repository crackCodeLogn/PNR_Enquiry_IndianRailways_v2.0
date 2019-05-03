package com.vv.PNR_Enquiry_IndianRailways.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 16-03-2018
 * @lastMod_details -> Changes made in order to accomodate the usage of checker framework's annotations
 * @since 04-08-2017
 */

//@SuppressWarnings("initialization.fields.uninitialized")
public class PassengerList {
    private List<Passenger> listOfPassengers;

    public PassengerList() {
        listOfPassengers = new ArrayList<>();
        //to deal with the suppress warning
    }

    public List<Passenger> getListOfPassengers() {
        return listOfPassengers;
    }

    public void setListOfPassengers(List<Passenger> listOfPassengers) {
        this.listOfPassengers = listOfPassengers;
    }
}