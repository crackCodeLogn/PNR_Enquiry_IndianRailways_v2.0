package com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer;

import com.vv.PNR_Enquiry_IndianRailways.LoggerFormatter;
import com.vv.PNR_Enquiry_IndianRailways.MapOfClassOfTravel;
import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This performs the dirty work of checking which PNR numbers are active and which can be used for debugging.
 * Also, deals with the extraction of the new format of the webpage, as this format is needed for crawling.
 *
 * This is a stand alone class for main logic testing and maintaining,
 * and the refined changes are reflected in the
 * @see PNR_EnquirerHttps
 *
 * @author Vivek
 * @version 1.0
 * @since 12-03-2018
 * @lastMod 17-03-2018
 */

public class DirtyRunner {

    private JFrame independantForJOptionPane = new JFrame();
    static ArrayList<String> faultyPNR = new ArrayList<String>();

    /**
     * This was a ranged sequence in order to facilitate wide analysis of the line numbers and ascertain
     * whether was the current version of the site, it'll work or break.
     *
     * This runs independent from the entire program, and is specifically for tweaking around
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String startPNR = "8425045249";
        String endPNR = "8425045250";
        long sPNR = Long.parseLong(startPNR);
        long ePNR = Long.parseLong(endPNR);
        int tr = 1;

        for (; sPNR <= ePNR; sPNR++, tr++) {
            System.out.println("Sending in the entry number : " + tr + " --> " + sPNR);
            new DirtyRunner().PNR_EnquirerHttpsRunner(Long.toString(sPNR));
        }
        System.out.println("NUMBER OF FLUSHED / NOT ASSIGNED PNR : " + faultyPNR.size());
        for (String fault : faultyPNR) {
            System.out.println(fault);
        }
    }

    /**
     * Main logic
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

        try {
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();

            responseCode = httpsURLConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                /*
            }
        } catch (Exception e) {
            //logger.info("Connection establishment failed... \nException : " + e);
        }

        //logger.info("Establishing inflow path now, if possible...");
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
                            //System.out.println("Elemented extraction : "+elemental);

                        } catch (Exception e2) {
                            //System.out.println("\t\tException e2 occuring in string conversion, e2 : " + e2);
                        }

                        if (lineNo >= 620 || line.contains("pnr-result-link col-sm-12")) break;

                        if (line.contains("pnr-bold-txt") && !firstShot) {
                            firstShot = true;
                        }

                        if (lineNo == 501) {
                            chartStatus = elemental;
                        }

                        if (lineNo == 526) {
                            trainNumber = elemental.substring(0, elemental.indexOf('-')).trim();
                            trainName = elemental.substring(elemental.indexOf('<') + 1).trim();
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

                try {
                    int num = Integer.parseInt(trainNumber);
                    //laying out the bones
                    System.out.println("------------------------");
                    System.out.println("train number : " + trainNumber);
                    System.out.println("train name : " + trainName);
                    System.out.println("from : " + boardingStation);
                    System.out.println("to : " + destinationStation);
                    System.out.println("date : " + boardingDate);
                    System.out.println("class : " + classOfTravel);
                    System.out.println("chart stat : " + chartStatus);
                    System.out.println("NUMBER OF PASSENGERS : " + numberOfPassengers);
                    //System.out.println("---");
                    System.out.println();
                    /*
                    //Refining the verbose output
                    for(String intermed : passengerDataToBeProcessed){
                        System.out.println("\t"+intermed);
                    }
                    */
                    List<Passenger> listOfPassengers = new ArrayList<Passenger>();
                    if (numberOfPassengers != 0) { //which shall never be true ideally
                        int localPointer = 0;
                        int tr = 0;
                        boolean shifter = true;
                        Passenger holder = null;
                        String elemental = "";
                        for (; localPointer < passengerDataToBeProcessed.size(); localPointer++) {
                            //for this version of the website, shifting the extraction on the basis of the string
                            if (passengerDataToBeProcessed.get(localPointer).contains("pnr-bold-txt")) {
                                elemental = passengerDataToBeProcessed.get(localPointer);
                                elemental = elemental.substring(elemental.indexOf('>') + 1, elemental.lastIndexOf('<')).trim();
                                if (shifter) {
                                    Passenger passenger = new Passenger();
                                    passenger.setNumber(tr++);
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
                            System.out.println(passenger.getNumber() + " :: " + passenger.getBookingStatus() + " :: " + passenger.getCurrentStatus());
                        }
                        System.out.println();
                    } else {
                        logger.info("THIS PART SHALL NOT BE REACHED EVER");
                    }
                } catch (Exception e) {
                    System.out.println("!!!!!!!!!!!!!!----------No record for this PNR----------!!!!!!!!!!!!!!");
                    faultyPNR.add(requestedPNR);
                }
            } else {
                logger.info("No reading of webpage as connection not ok!");
                logger.info("Connection response code : " + responseCode);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(independantForJOptionPane, "No Internet Connectivity or the server is down", "Error", JOptionPane.ERROR_MESSAGE);
            }
            httpsURLConnection.disconnect();
        } catch (Exception e) {
            logger.info("Exception in the reading part... \n\tException : " + e);
        }
    }
}