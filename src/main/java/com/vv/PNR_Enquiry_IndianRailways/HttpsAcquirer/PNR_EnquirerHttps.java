package com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer;

import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Prompt;
import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Result;
import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;
import com.vv.PNR_Enquiry_IndianRailways.Model.PassengerList;
import com.vv.PNR_Enquiry_IndianRailways.util.Helper;
import com.vv.PNR_Enquiry_IndianRailways.util.MapOfClassOfTravel;
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

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.TITLE_PNR_BASED_TICKET_DETAILS;
import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.siteToHitForPnrStatus;
import static com.vv.PNR_Enquiry_IndianRailways.util.Helper.showErrorMsgDialog;

/**
 * This deals with the Https Acquirer for the pnr request...
 *
 * @author Vivek
 * @version 1.0
 * @lastMod 16-03-2018
 * @lastMod_Details -> Annotated with checker framework requirement
 * -> Major structural overhaul :
 * -- Eradicating the use of the constructor and directly linking to the function {@link #PNR_EnquirerHttpsRunner(String)}
 * -- connection linkage line commenting from line 79 to line 89
 * -- Shifting parent component of the JOptionPane in line 343 and 350 from null to independentForJOptionPane
 * @since 01-08-2017
 */
public class PNR_EnquirerHttps {

    private final Logger logger = LoggerFactory.getLogger(PNR_EnquirerHttps.class);

    private final MapOfClassOfTravel mapOfClassOfTravel = new MapOfClassOfTravel();
    private final JFrame independentForJOptionPane = new JFrame();
    private final PNR_Prompt pnr_prompt;

    public PNR_EnquirerHttps(PNR_Prompt pnr_prompt) {
        this.pnr_prompt = pnr_prompt;
    }

    private void performEnablingFromHere() {
        pnr_prompt.disableAll = false;
        pnr_prompt.performEnabling();
    }

    /**
     * Main Logic
     *
     * @param requestedPNR
     * @throws IOException
     */
    public void PNR_EnquirerHttpsRunner(final String requestedPNR) throws IOException {

        int responseCode;
        URL url = new URL(siteToHitForPnrStatus + requestedPNR); //this https enabled site was allowing automated data extraction without giving any forbidden access response code
        logger.info("URL : {}, Establishing connection now!", url);
        @Nullable HttpsURLConnection httpsURLConnection;
        try {
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();

            responseCode = httpsURLConnection.getResponseCode();
            logger.info("Code received : {}", responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                logger.info("Connection established successfully!!");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                int lineNo = 1, numberOfPassengers = 0;
                String trainNumber = "", trainName = "", boardingStation = "", destinationStation = "", boardingDate = "", classOfTravel = "", chartStatus = "";
                String line;
                ArrayList<String> passengerDataToBeProcessed = new ArrayList<>();
                boolean firstShot = false;

                while ((line = bufferedReader.readLine()) != null) {

                    if (lineNo >= 480) {
                        String elemental = "";
                        logger.info("{} --> {}", lineNo, line);

                        try {
                            //elemental = line.substring(line.indexOf('>'), line.lastIndexOf('<')).trim();
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
                            //System.out.println("\t\tException e2 occuring in string conversion, e2 : ", e2);
                            //commented out, as this would occur spam the output stream
                        }

                        //if (lineNo >= 620) break;
                        //restricting the upper limit on the line reading to 620, as there can be only max 4 passengers on a single ticket, and the last entry should end at 612
                        if (lineNo >= 620 || line.contains("pnr-result-link col-sm-12")) break;

                        //if (line.contains("PNR - ", requestedPNR)) {
                        if (line.contains("pnr-bold-txt") && !firstShot) {
                            //baseLineMark = lineNo + 3;
                            //baseLineMark = lineNo;
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
                            trainName = elemental.substring(elemental.indexOf('-') + 1).trim();
                        }

                        if (lineNo == 531) boardingStation = elemental;
                        if (lineNo == 536) destinationStation = elemental;
                        if (lineNo == 548) boardingDate = elemental;
                        if (lineNo == 552) classOfTravel = elemental;

                        /*
                        //this was for the older version of the site, when this repo was last updated 7 months ago
                        if (lineNo == baseLineMark) {
                            System.out.println("PNR first step crossed at line number : ", lineNo + ", and the base mark : ", baseLineMark);
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

                /*
                //older code
                while ((line = bufferedReader.readLine()) != null) {
                    logger.info("Line number ", lineNo + " --> ", line);

                    //if (lineNo >= 606) {
                    //if (lineNo >= 640) {
                    if (lineNo >= 600) {

                        logger.info("Line number ", lineNo + " --> ", line);
                        //if (lineNo >= 670 && line.contains("</table>")) break;
                        //if (lineNo >= 702 && line.contains("</table>")) break;
                        if (lineNo >= 700 && line.contains("</table>")) break;
                        //System.out.println("Passenger details reached status : "+passengerDetailsReached+", at line no : "+lineNo);
                        //if (passengerDetailsReached && line.contains("</table>")) break;

                        //starting the data extraction from this line onwards
                        //if (lineNo == 609) {
                        //if (lineNo == 643) {
                        if (line.contains("PNR - ", requestedPNR)) {
                            baseLineMark = lineNo + 3;
                        }
                        if (lineNo == baseLineMark) {
                            System.out.println("PNR first step crossed at line number : ", lineNo + ", and the base mark : ", baseLineMark);
                            trainNumber = line.substring(0, line.indexOf('-')).trim();
                            trainName = line.substring(line.indexOf('-') + 1).trim();
                        }
                        //if (lineNo == 626) boardingStation = line.trim();
                        //if (lineNo == 660) boardingStation = line.trim();
                        if (lineNo == baseLineMark + 17) boardingStation = line.trim();
                        //if (lineNo == 635) destinationStation = line.trim();
                        //if (lineNo == 669) destinationStation = line.trim();
                        if (lineNo == baseLineMark + 26) destinationStation = line.trim();
                        //if (lineNo == 642) boardingDate = line.trim();
                        //if (lineNo == 676) boardingDate = line.trim();
                        if (lineNo == baseLineMark + 33) boardingDate = line.trim();
                        //if (lineNo == 648) classOfTravel = line.trim();
                        //if (lineNo == 682) classOfTravel = line.trim();
                        if (lineNo == baseLineMark + 39) classOfTravel = line.trim();
                        //if (lineNo == 654) chartStatus = line.trim();
                        //if (lineNo == 688) chartStatus = line.trim();
                        if (lineNo == baseLineMark + 45) chartStatus = line.trim();
                        //if (lineNo >= 672) {
                        //if (lineNo >= 706) {
                        if (lineNo >= baseLineMark + 63) {
                            //passengerDetailsReached = true;
                            passengerDataToBeProcessed.add(line.trim());
                            if (line.contains("</tr>")) numberOfPassengers++;
                        }
                    }
                    lineNo++;
                }
                */
                bufferedReader.close();
                httpsURLConnection.disconnect();

                logger.info("The extracted details:-");
                logger.info("Train number : {}", trainNumber);
                logger.info("Train name : {}", trainName);
                logger.info("Boarding station : {}", boardingStation);
                logger.info("Destination station : {}", destinationStation);
                logger.info("Boarding date : {}", boardingDate);
                logger.info("Class : {}", classOfTravel);
                logger.info("Chart status : {}", chartStatus);
                //logger.info("Data to be processed :- \n"+passengerDataToBeProcessed);
                logger.info("The number of passengers in this ticket : {}", numberOfPassengers);

                //starting the passenger data processing if everything is working properly
                List<Passenger> listOfPassengers = new ArrayList<>();
                if (numberOfPassengers != 0) {
                    int localPointer = 0;
                    int tr = 0;
                    boolean shifter = true;
                    Passenger holder = null;
                    String elemental;
                    //Extracting the details of the passengers on the ticket
                    for (; localPointer < passengerDataToBeProcessed.size(); localPointer++) {
                        //for this version of the website, shifting the extraction on the basis of the string
                        //logger.info(passengerDataToBeProcessed.get(localPointer));
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
                                holder.setCurrentStatus(elemental);
                                listOfPassengers.add(holder);
                                shifter = true;
                            }
                        }
                    }
                    listOfPassengers.forEach(passenger -> logger.info(passenger.toString()));

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

                    SwingUtilities.invokeLater(() -> {

                        JFrame frame = new JFrame(TITLE_PNR_BASED_TICKET_DETAILS);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        //frame.setLocationRelativeTo(null);
                        frame.getContentPane().add(new PNR_Result(requestedPNR, finalTrainNumber, finalTrainName, finalBoardingStation, finalDestinationStation, finalBoardingDate, finalClassOfTravel, finalChartStatus, passengerList, mapOfClassOfTravel).getUI());

                        // Create and set up the content pane.
                        // Display the window.
                        frame.pack();
                        frame.setIconImage(Helper.smallIconForFrame);
                        frame.setResizable(false);
                        frame.setVisible(true);
                    });
                } else {
                    logger.warn("THIS PART SHALL NOT BE REACHED EVER");
                    showErrorMsgDialog(independentForJOptionPane, "PNR record doesn't exist!");
                }
            } else {
                logger.error("No reading of webpage as connection not ok! (Conn resp code : {})", responseCode);
                showErrorMsgDialog(independentForJOptionPane, "No Internet Connectivity / The server is down");
            }
            httpsURLConnection.disconnect();
            performEnablingFromHere();
        } catch (Exception e) {
            logger.info("Exception in the reading part... ", e);
            performEnablingFromHere();
        }
    }
}