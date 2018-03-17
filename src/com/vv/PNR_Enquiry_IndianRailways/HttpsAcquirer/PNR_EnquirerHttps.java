package com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer;

import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Form;
import com.vv.PNR_Enquiry_IndianRailways.LoggerFormatter;
import com.vv.PNR_Enquiry_IndianRailways.MainActivity;
import com.vv.PNR_Enquiry_IndianRailways.MapOfClassOfTravel;
import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;
import com.vv.PNR_Enquiry_IndianRailways.Model.PassengerList;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.vv.PNR_Enquiry_IndianRailways.MainActivity.smallLogoPath;

/**
 * This deals with the Https Acquirer for the pnr request...
 *
 * @author Vivek
 * @version 1.0
 * @since 01-08-2017
 * @lastMod 17-03-2018
 */
public class PNR_EnquirerHttps {

    private JFrame independantForJOptionPane = new JFrame();

    /**
     * Main Logic
     *
     * @param requestedPNR
     * @throws IOException
     */
    public void PNR_EnquirerHttpsRunner(final String requestedPNR) throws IOException {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PNR_EnquirerHttps.class.getName());
        new MapOfClassOfTravel();
        //initializing the map which stores the class and their corresponding description
        logger = LoggerFormatter.formatTheLoggerOutput(logger);
        //the above line should be written in each and every class where logging is to be used

        int responseCode = 404;

        HttpsURLConnection httpsURLConnection = null;
        URL url = new URL("https://www.railyatri.in/pnr-status/" + requestedPNR); //this https enabled site was allowing automated data extraction without giving any forbidden access response code
        logger.info("URL : " + url.toString());

        logger.info("Establishing connection now!");
        try {
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();

            responseCode = httpsURLConnection.getResponseCode();
            logger.info("Code received : " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                logger.info("Connection established successfully!!");
                /*
            }
        } catch (Exception e) {
            logger.info("Connection establishment failed... \nException : " + e);
            performEnablingFromHere();
        }

        logger.info("Establishing inflow path now, if possible...");
        try {
            if (responseCode == HttpsURLConnection.HTTP_OK) {
            */
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                int lineNo = 1, numberOfPassengers = 0, baseLineMark = 0;
                String trainNumber = "", trainName = "", boardingStation = "", destinationStation = "", boardingDate = "", classOfTravel = "", chartStatus = "";
                String line;
                ArrayList<String> passengerDataToBeProcessed = new ArrayList<String>();
                boolean firstShot = false;

                while ((line = bufferedReader.readLine()) != null) {

                    if (lineNo >= 480) {
                        String elemental = "";
                        logger.info(lineNo + " --> " + line);

                        try {
                            //below code considering that for every opening bracket in a line there is a closing brace on the same for the same
                            int i, endingTag = 0;
                            for (i = 0; i < line.length(); i++) {
                                if (line.charAt(i) == '>') endingTag++;
                            }
                            int tr = 0;
                            for (i = 0; i < line.length(); i++) {
                                if (line.charAt(i) == '>') {
                                    tr++;
                                    if (tr == endingTag / 2) {
                                        elemental = line.substring(i + 1);
                                        break;
                                    }
                                }
                            }
                            elemental = elemental.substring(0, elemental.indexOf('<'));

                        } catch (Exception e2) {
                            //System.out.println("\t\tException e2 occuring in string conversion, e2 : " + e2);
                            //commented out, as this would occur spam the output stream
                        }

                        //restricting the upper limit on the line reading to 620, as there can be only max 4 passengers on a single ticket, and the last entry should end at 612
                        if (lineNo >= 620 || line.contains("pnr-result-link col-sm-12")) break;

                        if (line.contains("pnr-bold-txt") && !firstShot) {
                            firstShot = true;
                        }

                        if (lineNo == 501) {
                            chartStatus = elemental;
                        }

                        if (lineNo == 526) {
                            trainNumber = elemental.substring(0, elemental.indexOf('-')).trim();
                            trainName = elemental.substring(elemental.indexOf('-') + 1).trim();
                        }

                        if (lineNo == 531) boardingStation = elemental;
                        if (lineNo == 536) destinationStation = elemental;
                        if (lineNo == 548) boardingDate = elemental;
                        if (lineNo == 552) classOfTravel = elemental;

                        if (lineNo >= 574) {
                            passengerDataToBeProcessed.add(line.trim());
                            if (line.contains("chart-stats")) numberOfPassengers++;
                        }

                    }
                    lineNo++;
                }

                bufferedReader.close();
                httpsURLConnection.disconnect();

                logger.info("The extracted details:-");
                logger.info("Train number : " + trainNumber);
                logger.info("Train name : " + trainName);
                logger.info("Boarding station : " + boardingStation);
                logger.info("Destination station : " + destinationStation);
                logger.info("Boarding date : " + boardingDate);
                logger.info("Class : " + classOfTravel);
                logger.info("Chart status : " + chartStatus);
                //logger.info("Data to be processed :- \n"+passengerDataToBeProcessed);
                logger.info("The number of passengers in this ticket : " + numberOfPassengers);

                //starting the passenger data processing if everything is working properly
                List<Passenger> listOfPassengers = new ArrayList<Passenger>();
                if (numberOfPassengers != 0) {
                    int localPointer = 0;
                    int tr = 0;
                    boolean shifter = true;
                    Passenger holder = null;
                    String elemental = "";
                    //Extracting the details of the passengers on the ticket
                    for (; localPointer < passengerDataToBeProcessed.size(); localPointer++) {
                        //for this version of the website, shifting the extraction on the basis of the string
                        if (passengerDataToBeProcessed.get(localPointer).contains("pnr-bold-txt")) {
                            elemental = passengerDataToBeProcessed.get(localPointer);
                            elemental = elemental.substring(elemental.indexOf('>') + 1, elemental.lastIndexOf('<')).trim();
                            if (shifter) {
                                Passenger passenger = new Passenger();
                                passenger.setNumber(++tr);
                                holder = passenger;
                                passenger.setBookingStatus(elemental);
                                shifter = false;
                            } else {
                                if(holder!=null) {
                                    holder.setCurrentStatus(elemental);
                                    listOfPassengers.add(holder);
                                }
                                shifter = true;
                            }
                        }
                    }
                    for (Passenger passenger : listOfPassengers) {
                        logger.info(passenger.getNumber() + " :: " + passenger.getBookingStatus() + " :: " + passenger.getCurrentStatus());
                    }
                    final PassengerList passengerList = new PassengerList();
                    passengerList.setListOfPassengers(listOfPassengers);
                    //call the function for the pnr form from here
                    final String finalTrainNumber = trainNumber;
                    final String finalTrainName = trainName;
                    final String finalChartStatus = chartStatus;
                    final String finalClassOfTravel = classOfTravel;
                    final String finalBoardingDate = boardingDate;
                    final String finalDestinationStation = destinationStation;
                    final String finalBoardingStation = boardingStation;

                    final java.util.logging.Logger finalLogger = logger;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            JFrame frame = new JFrame("PNR based ticket details");
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            frame.setLocationRelativeTo(null);
                            frame.getContentPane().add(new PNR_Form(requestedPNR, finalTrainNumber, finalTrainName, finalBoardingStation, finalDestinationStation, finalBoardingDate, finalClassOfTravel, finalChartStatus, passengerList).getUI());

                            // Create and set up the content pane.
                            // Display the window.
                            frame.pack();
                            try {
                                frame.setIconImage(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainActivity.class.getResource(smallLogoPath))).getImage());
                            } catch (NullPointerException npe1) {
                                finalLogger.info("ERROR in loading the image for the frame... NUll pointer exception occurred!");
                            }
                            frame.setResizable(false);
                            frame.setVisible(true);
                        }
                    });
                } else {
                    logger.info("THIS PART SHALL NOT BE REACHED EVER");
                    //logger.info("No passengers, so no processing of their data!");
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(independantForJOptionPane, "PNR record doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                logger.info("No reading of webpage as connection not ok!");
                logger.info("Connection response code : " + responseCode);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(independantForJOptionPane, "No Internet Connectivity or the server is down", "Error", JOptionPane.ERROR_MESSAGE);
            }
            httpsURLConnection.disconnect();
            //loopON = false;
            performEnablingFromHere();
        } catch (Exception e) {
            logger.info("Exception in the reading part... \n\tException : " + e);
            performEnablingFromHere();
            //loopON = false;
        }
    }

    public static void performEnablingFromHere() {
        MainActivity.disableAll = false;
        MainActivity.performEnabling();
    }
}