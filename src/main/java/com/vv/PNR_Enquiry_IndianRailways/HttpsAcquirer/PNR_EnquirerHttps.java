package com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer;

import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Prompt;
import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Result;
import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;
import com.vv.PNR_Enquiry_IndianRailways.Model.PassengerList;
import com.vv.PNR_Enquiry_IndianRailways.util.Helper;
import com.vv.PNR_Enquiry_IndianRailways.util.MapOfClassOfTravel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.*;
import static com.vv.PNR_Enquiry_IndianRailways.util.Helper.showErrorMsgDialog;

/**
 * This deals with the Https Acquirer for the pnr request...
 *
 * @author Vivek
 * @version 1.0
 * @lastMod 04-05-2019
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

    private String refiner(String htmlText) {
        return Jsoup.clean(htmlText, Whitelist.none()).trim();
    }

    private String refiner(Element element) {
        return refiner(element.toString());
    }

    /**
     * Main Logic
     *
     * @param requestedPNR
     */
    public void PNR_EnquirerHttpsRunner(final String requestedPNR) {
        final String url = siteToHitForPnrStatus + requestedPNR;
        logger.info("URL : {}, Establishing connection now!", url);

        try {
            final Document document = Jsoup.connect(url).get();

            String data = refiner(document.getElementsByClass(CLASS_TRAIN_NUMBER_NAME).first());
            data = data.substring(data.indexOf(TRAIN_NAME_NAME_SPLIT) + TRAIN_NAME_NAME_SPLIT.length());
            final String trainNumber = refiner(data.substring(0, data.indexOf(SPLITTER_HYPHEN)));
            final String trainName = refiner(data.substring(data.indexOf(SPLITTER_HYPHEN) + 2));

            Elements classesList = document.getElementsByClass(CLASS_BOARDING_DESTINATION).first().getElementsByClass(CLASS_EXTRACTION_POINT);
            final String boardingStation = refiner(classesList.get(0));
            final String destinationStation = refiner(classesList.get(1));

            classesList = document.getElementsByClass(CLASS_DATE_CLASS_TRAVEL).first().getElementsByClass(CLASS_EXTRACTION_POINT);
            final String boardingDate = refiner(classesList.get(0));
            final String classOfTravel = refiner(classesList.get(1));

            final String chartStatus = refiner(document.getElementsByClass(CLASS_CHART_STATUS).first());

            classesList = document.getElementsByClass(CLASS_PSGN_STATS);
            classesList.remove(0); //this might break it
            final int numberOfPassengers = classesList.size();
            final List<Element> passengerDataToBeProcessed = new ArrayList<>(classesList);

            logger.info("The extracted details:-");
            logger.info("PNR : {}", requestedPNR);
            logger.info("Train number : {}", trainNumber);
            logger.info("Train name : {}", trainName);
            logger.info("Boarding station : {}", boardingStation);
            logger.info("Destination station : {}", destinationStation);
            logger.info("Boarding date : {}", boardingDate);
            logger.info("Class : {}", classOfTravel);
            logger.info("Chart status : {}", chartStatus);
            logger.info("The number of passengers in this ticket : {}", numberOfPassengers);

            //starting the passenger data processing if everything is working properly
            if (numberOfPassengers > 0) { //which shall never be true ideally
                final List<Passenger> listOfPassengers = new ArrayList<>();
                final AtomicInteger passengerNumber = new AtomicInteger(1);

                passengerDataToBeProcessed.forEach(element -> {
                    final Passenger passenger = new Passenger();
                    final Elements internal = element.getElementsByClass(CLASS_EXTRACTION_POINT);
                    passenger.setNumber(passengerNumber.getAndIncrement());
                    passenger.setBookingStatus(refiner(internal.get(0)));
                    passenger.setCurrentStatus(refiner(internal.get(1)));
                    listOfPassengers.add(passenger);
                });
                listOfPassengers.forEach(passenger -> logger.info(passenger.toString()));

                final PassengerList passengerList = new PassengerList();
                passengerList.setListOfPassengers(listOfPassengers);

                //calling for pnr result goes from here
                SwingUtilities.invokeLater(() -> {

                    JFrame frame = new JFrame(TITLE_PNR_BASED_TICKET_DETAILS);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    //frame.setLocationRelativeTo(null);
                    frame.getContentPane().add(new PNR_Result(requestedPNR, trainNumber, trainName, boardingStation, destinationStation, boardingDate, classOfTravel, chartStatus, passengerList, mapOfClassOfTravel).getUI());

                    frame.pack();
                    frame.setIconImage(Helper.smallIconForFrame);
                    frame.setResizable(false);
                    frame.setVisible(true);
                });

                performEnablingFromHere();
            } else {
                logger.warn("THIS PART SHALL NOT BE REACHED EVER");
                showErrorMsgDialog(independentForJOptionPane, "PNR record doesn't exist!");
            }
        } catch (Exception e) {
            logger.info("Exception in the reading part... ", e);
            performEnablingFromHere();

            logger.error("No reading of webpage as connection not ok! Error : ", e);
            showErrorMsgDialog(independentForJOptionPane, "No Internet Connectivity / The server is down");
        }
    }
}