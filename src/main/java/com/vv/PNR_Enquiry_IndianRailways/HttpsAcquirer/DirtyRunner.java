package com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer;

import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;
import com.vv.PNR_Enquiry_IndianRailways.util.Helper;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This performs the dirty work of checking which PNR numbers are active and which can be used for debugging.
 * Also, deals with the extraction of the new format of the webpage, as this format is needed for crawling.
 * <p>
 * This is a stand alone class for main logic testing and maintaining,
 * and the refined changes are reflected in the
 *
 * @author Vivek
 * @version 1.0
 * @lastMod 16-03-2018
 * @lastMod_Details -> Annotated with checker framework requirement
 * -> Major structural overhaul :
 * -- Eradicating the use of the constructor and directly linking to the @function PNR_EnquirerHttpsRunner()
 * -- connection linkage line commenting from line 108 to line 117
 * -- Shifting parent component of the JOptionPane in line 294 from null to independantForJOptionPane
 * @see PNR_EnquirerHttps
 * @since 12-03-2018
 */

@Deprecated
public class DirtyRunner {

    private static final Logger logger = LoggerFactory.getLogger(DirtyRunner.class);

    static ArrayList<String> faultyPNR = new ArrayList<>();
    private JFrame independantForJOptionPane = new JFrame();

    /**
     * This was a ranged sequence in order to facilitate wide analysis of the line numbers and ascertain
     * whether was the current version of the site, it'll work or break.
     * <p>
     * This runs independent from the entire program, and is specifically for tweaking around
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String startPNR = "8505631000";
        String endPNR = "8505631005";
        long sPNR = Long.parseLong(startPNR);
        long ePNR = Long.parseLong(endPNR);
        int tr = 1;

        for (; sPNR <= ePNR; sPNR++, tr++) {
            logger.info("Sending in the entry number : {} --> ", tr, sPNR);
            new DirtyRunner().PNR_EnquirerHttpsRunner(Long.toString(sPNR));
        }
        logger.info("NUMBER OF FLUSHED / NOT ASSIGNED PNR : {}", faultyPNR.size());
        faultyPNR.forEach(logger::info);
    }

    /**
     * Main logic
     *
     * @param requestedPNR
     * @throws IOException
     */
    public void PNR_EnquirerHttpsRunner(@NonNull final String requestedPNR) throws IOException {
        int responseCode;

        @Nullable HttpsURLConnection httpsURLConnection;
        URL url = new URL("https://www.railyatri.in/pnr-status/" + requestedPNR); //this https enabled site was allowing automated data extraction without giving any forbidden access response code
        try {
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();

            responseCode = httpsURLConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                int lineNo = 1, numberOfPassengers = 0;
                String trainNumber = "", trainName = "", boardingStation = "", destinationStation = "", boardingDate = "", classOfTravel = "", chartStatus = "";
                String line;
                ArrayList<String> passengerDataToBeProcessed = new ArrayList<>();

                boolean firstShot = false;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(lineNo + " -->> " + line);
                    //if (lineNo >= 600) {
                    if (false) {
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
                            logger.info("Elemented extraction : " + elemental);

                        } catch (Exception e2) {
                            logger.error("\t\tException e2 occuring in string conversion, e2 : " + e2);
                        }

                        //if (lineNo >= 620) break;
                        //restricting the upper limit on the line reading to 620, as there can be only max 4 passengers on a single ticket, and the last entry should end at 612
                        if (lineNo >= 620 || line.contains("pnr-result-link col-sm-12")) break;

                        //if (line.contains("PNR - " + requestedPNR)) {
                        if (line.contains("pnr-bold-txt") && !firstShot) {
                            //baseLineMark = lineNo + 3;
                            firstShot = true;
                        }

                        /*
                        if(lineNo == 497 ){
                            if(elemental.equals("CHART NOT PREPARED"))
                                chartStatus = "NOT PREPARED";
                            else if(elemental.equals("CURRENT STATUS"))
                                chartStatus = "PREPARED";
                        }
                        */

                        if (lineNo == 501) {
                            //if(elemental.equals("CHART NOT PREPARED"))
                            chartStatus = elemental;
                            //else if(elemental.equals("CURRENT STATUS"))
                            //chartStatus = "PREPARED";
                        }

                        if (lineNo == 526) {
                            trainNumber = elemental.substring(0, elemental.indexOf('-')).trim();
                            trainName = elemental.substring(elemental.indexOf('<') + 1).trim();
                        }

                        if (lineNo == 531) boardingStation = elemental;
                        if (lineNo == 536) destinationStation = elemental;
                        if (lineNo == 548) boardingDate = elemental;
                        if (lineNo == 552) classOfTravel = elemental;

                        /*
                        //this was for the older version of the site, when this repo was last updated 7 months ago
                        if (lineNo == baseLineMark) {
                            logger.info("PNR first step crossed at line number : " + lineNo + ", and the base mark : " + baseLineMark);
                            trainNumber = line.substring(0, line.indexOf('-')).trim();
                            trainName = line.substring(line.indexOf('-') + 1).trim();
                        }
                        if (lineNo == baseLineMark + 17) boardingStation = line.trim();
                        if (lineNo == baseLineMark + 26) destinationStation = line.trim();
                        if (lineNo == baseLineMark + 33) boardingDate = line.trim();
                        if (lineNo == baseLineMark + 39) classOfTravel = line.trim();
                        if (lineNo == baseLineMark + 45) chartStatus = line.trim();
                        */
                        //if (lineNo >= baseLineMark + 63) {
                        if (lineNo >= 574) {
                            passengerDataToBeProcessed.add(line.trim());
                            //if (line.contains("</tr>")) numberOfPassengers++;
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
                    logger.info("------------------------");
                    logger.info("train number : " + trainNumber);
                    logger.info("train name : " + trainName);
                    logger.info("from : " + boardingStation);
                    logger.info("to : " + destinationStation);
                    logger.info("date : " + boardingDate);
                    logger.info("class : " + classOfTravel);
                    logger.info("chart stat : " + chartStatus);
                    logger.info("NUMBER OF PASSENGERS : " + numberOfPassengers);
                    logger.info("---");
                    List<Passenger> listOfPassengers = new ArrayList<Passenger>();
                    if (numberOfPassengers != 0) { //which shall never be true ideally
                        int localPointer = 0;
                        int tr = 0;
                        boolean shifter = true;
                        @Nullable Passenger holder = null;
                        String elemental;
                        for (; localPointer < passengerDataToBeProcessed.size(); localPointer++) {
                            //for this version of the website, shifting the extraction on the basis of the string
                            //logger.info(passengerDataToBeProcessed.get(localPointer));
                            if (passengerDataToBeProcessed.get(localPointer).contains("pnr-bold-txt")) {
                                elemental = passengerDataToBeProcessed.get(localPointer);
                                elemental = elemental.substring(elemental.indexOf('>') + 1, elemental.lastIndexOf('<')).trim();
                                if (shifter) {
                                    Passenger passenger = new Passenger();
                                    //passenger.setNumber(Integer.parseInt(passengerDataToBeProcessed.get(localPointer)));
                                    passenger.setNumber(tr++);
                                    holder = passenger;
                                    //passenger.setBookingStatus(passengerDataToBeProcessed.get(localPointer + 3).trim());
                                    passenger.setBookingStatus(elemental);
                                    shifter = false;
                                } else {
                                    //passenger.setCurrentStatus(passengerDataToBeProcessed.get(localPointer + 6).trim());
                                    if (holder != null) {
                                        holder.setCurrentStatus(elemental);
                                        listOfPassengers.add(holder);
                                    }
                                    shifter = true;
                                }
                            }
                        }
                        for (Passenger passenger : listOfPassengers) {
                            //logger.info(passenger.getNumber() + " :: " + passenger.getBookingStatus() + " :: " + passenger.getCurrentStatus());
                            logger.info(passenger.getNumber() + " :: " + passenger.getBookingStatus() + " :: " + passenger.getCurrentStatus());
                        }
                    } else {
                        logger.info("THIS PART SHALL NOT BE REACHED EVER");
                    }
                } catch (Exception e) {
                    logger.info("!!!!!!!!!!!!!!----------No record for this PNR----------!!!!!!!!!!!!!!");
                    faultyPNR.add(requestedPNR);
                }
            } else {
                logger.info("No reading of webpage as connection not ok!");
                logger.info("Connection response code : " + responseCode);
                Helper.showErrorMsgDialog(independantForJOptionPane, "No Internet Connectivity or the server is down");
            }
            httpsURLConnection.disconnect();
        } catch (Exception e) {
            logger.info("Exception in the reading part... \n\tException : " + e);
        }

    }
}