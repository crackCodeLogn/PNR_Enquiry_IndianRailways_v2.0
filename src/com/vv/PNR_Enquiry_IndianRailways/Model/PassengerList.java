package com.vv.PNR_Enquiry_IndianRailways.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-08-2017
 * @lastMod 16-03-2018
 */

public class PassengerList {
    private List<Passenger> listOfPassengers;

    public List<Passenger> getListOfPassengers() {
        return listOfPassengers;
    }

    public void setListOfPassengers(List<Passenger> listOfPassengers) {
        this.listOfPassengers = listOfPassengers;
    }

    public PassengerList() {

    }
}