package com.vv.PNR_Enquiry_IndianRailways.GUI;

import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;
import com.vv.PNR_Enquiry_IndianRailways.Model.PassengerList;
import com.vv.PNR_Enquiry_IndianRailways.util.MapOfClassOfTravel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.EMPTY_STR;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
@RunWith(JUnit4.class)
public class PNR_ResultTest extends ForGUI_Test {

    private PassengerList passengerList;
    private MapOfClassOfTravel mapOfClassOfTravel;

    @Before
    public void before() {
        Passenger p1 = new Passenger(1, "WL/8", "CNF");
        Passenger p2 = new Passenger(2, "WL/2", "CNF");
        Passenger p3 = new Passenger(3, "WL/1", "CNF");
        List<Passenger> passengerArrayList = new ArrayList<>();
        passengerArrayList.add(p1);
        passengerArrayList.add(p2);
        passengerArrayList.add(p3);
        passengerList = new PassengerList();
        passengerList.setListOfPassengers(passengerArrayList);

        mapOfClassOfTravel = new MapOfClassOfTravel();
    }

    @Test
    public void launchUI() {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PNR sample product");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().add(new PNR_Result(EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR, passengerList, mapOfClassOfTravel).getUI());

            // Display the window.
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }

}