package com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer;

import com.vv.PNR_Enquiry_IndianRailways.Model.Passenger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
public class DirtyRunnerLatest {

    private static final Logger logger = LoggerFactory.getLogger(DirtyRunnerLatest.class);

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
            new DirtyRunnerLatest().PNR_EnquirerHttpsRunner(Long.toString(sPNR));
        }
        logger.info("NUMBER OF FLUSHED / NOT ASSIGNED PNR : {}", faultyPNR.size());
        faultyPNR.forEach(logger::info);
    }

    private String cleaner(String htmlText) {
        return Jsoup.clean(htmlText, Whitelist.none());
    }

    private String cleaner(Element element) {
        return cleaner(element.toString());
    }

    /**
     * Main logic
     *
     * @param requestedPNR
     * @throws IOException
     */
    public void PNR_EnquirerHttpsRunner(@NonNull final String requestedPNR) throws IOException {

        URL url = new URL("https://www.railyatri.in/pnr-status/" + requestedPNR); //this https enabled site was allowing automated data extraction without giving any forbidden access response code

        Document document = Jsoup.connect(url.toString()).get();
        //Document document = Jsoup.parse(text);
        try {

            String data = cleaner(document.getElementsByClass(" col-xs-12 train-info").first());
            data = data.substring(data.indexOf("NAME") + "NAME".length());
            String trainNumber = data.substring(0, data.indexOf('-')).trim();
            String trainName = data.substring(data.indexOf('-') + 2).trim();

            Elements classesList = document.getElementsByClass("train-route").first().getElementsByClass("pnr-bold-txt");

            String boardingStation = cleaner(classesList.get(0));
            String destinationStation = cleaner(classesList.get(1));


            classesList = document.getElementsByClass("boarding-detls").first().getElementsByClass("pnr-bold-txt");

            String boardingDate = cleaner(classesList.get(0));
            String classOfTravel = cleaner(classesList.get(1));

            String chartStatus = cleaner(document.getElementsByClass("chart-status-txt").first());
            classesList = document.getElementsByClass("chart-stats");
            int numberOfPassengers = classesList.size();
            numberOfPassengers = numberOfPassengers >= 2 ? numberOfPassengers - 1 : numberOfPassengers;

            classesList.remove(0);
            List<Element> passengerDataToBeProcessed = new ArrayList<>(classesList);
            //laying out the bones
            logger.info("------------------------");
            logger.info("PNR : {}", requestedPNR);
            logger.info("train number : " + trainNumber);
            logger.info("train name : " + trainName);
            logger.info("from : " + boardingStation);
            logger.info("to : " + destinationStation);
            logger.info("date : " + boardingDate);
            logger.info("class : " + classOfTravel);
            logger.info("chart stat : " + chartStatus);
            logger.info("NUMBER OF PASSENGERS : " + numberOfPassengers);
            logger.info("---");
            List<Passenger> listOfPassengers = new ArrayList<>();
            if (numberOfPassengers != 0) { //which shall never be true ideally
                AtomicInteger passengerNumber = new AtomicInteger(1);
                passengerDataToBeProcessed.forEach(element -> {
                    Passenger passenger = new Passenger();
                    Elements internal = element.getElementsByClass("pnr-bold-txt");
                    passenger.setNumber(passengerNumber.getAndIncrement());
                    passenger.setBookingStatus(cleaner(internal.get(0)));
                    passenger.setCurrentStatus(cleaner(internal.get(1)));
                    listOfPassengers.add(passenger);
                });
                listOfPassengers.forEach(passenger -> logger.info(passenger.toString()));
            } else {
                logger.info("THIS PART SHALL NOT BE REACHED EVER");
            }
        } catch (Exception e) {
            logger.info("!!!!!!!!!!!!!!----------No record for PNR {}----------!!!!!!!!!!!!!!", requestedPNR);
            logger.error("Error : ", e);
            faultyPNR.add(requestedPNR);
        }
    }
}