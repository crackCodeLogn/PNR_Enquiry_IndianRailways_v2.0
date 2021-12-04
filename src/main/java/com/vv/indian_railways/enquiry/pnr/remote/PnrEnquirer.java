package com.vv.indian_railways.enquiry.pnr.remote;

import com.vv.indian_railways.enquiry.pnr.model.Ticket;
import com.vv.indian_railways.enquiry.pnr.remote.feign.PnrEnquiryClient;
import com.vv.indian_railways.enquiry.pnr.ui.PnrPrompt;
import com.vv.indian_railways.enquiry.pnr.ui.PnrResult;
import com.vv.indian_railways.enquiry.pnr.util.Helper;
import feign.Feign;
import feign.gson.GsonDecoder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.Date;

import static com.vv.indian_railways.enquiry.pnr.constants.Constants.SITE_TO_HIT_FOR_PNR_STATUS;
import static com.vv.indian_railways.enquiry.pnr.util.Helper.showErrorMsgDialog;

/**
 * This deals with the Https Acquirer for the pnr request...
 *
 * @author Vivek
 * @version 1.0
 * @lastMod 04-12-2021
 * @since 01-08-2017
 */
@Slf4j
public class PnrEnquirer {
    private static final String TITLE_PNR_BASED_TICKET_DETAILS = "PNR based ticket details";

    private final JFrame independentForJOptionPane;
    private final PnrPrompt pnrPrompt;
    private final PnrEnquiryClient pnrEnquiryClient;

    public PnrEnquirer(PnrPrompt pnrPrompt) {
        this.pnrPrompt = pnrPrompt;

        independentForJOptionPane = new JFrame();
        pnrEnquiryClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(PnrEnquiryClient.class, SITE_TO_HIT_FOR_PNR_STATUS);
    }

    /**
     * Main Logic
     * TODO - decouple get and display later
     *
     * @param requestedPnr
     */
    public void getPnrDetailAndDisplay(final String requestedPnr) {
        try {
            log.info("Going to hit '{}' for getting pnr details of '{}'", SITE_TO_HIT_FOR_PNR_STATUS, requestedPnr);
            Ticket ticket;
            try {
                ticket = pnrEnquiryClient.getTicketDetail(requestedPnr).getData();
            } catch (Exception e) {
                log.error("Failed to get data for '{}'. ", requestedPnr, e);
                enablePnrPrompt();
                showErrorMsgDialog(independentForJOptionPane, "Failed to get data for " + requestedPnr);
                return;
            }
            final String trainNumber = ticket.getTrainNumber();
            final String trainName = ticket.getTrainName();
            final String boardingStation = String.format("%s | %s", ticket.getBoardingStationName(), ticket.getBoardingStationCode());
            final String destinationStation = String.format("%s | %s", ticket.getDeboardingStationName(), ticket.getDeboardingStationCode());
            final String boardingDate = (new Date(ticket.getScheduledBoardTime())).toString();
            final String classOfTravel = ticket.getFareClass();
            final String chartStatus = ticket.isChartPrepared() ? "PREPARED" : "NOT PREPARED";

            log.info("The extracted details:-");
            log.info("PNR : {}", requestedPnr);
            log.info("Train number : {}", trainNumber);
            log.info("Train name : {}", trainName);
            log.info("Boarding station : {}", boardingStation);
            log.info("Destination station : {}", destinationStation);
            log.info("Boarding date : {}", boardingDate);
            log.info("Class : {}", classOfTravel);
            log.info("Chart status : {}", chartStatus);
            log.info("The number of passengers in this ticket : {}", ticket.getPassengers().size());

            if (!ticket.getPassengers().isEmpty()) {
                //calling for pnr result goes from here
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame(TITLE_PNR_BASED_TICKET_DETAILS);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.getContentPane().add(new PnrResult(requestedPnr, trainNumber, trainName, boardingStation, destinationStation,
                            boardingDate, classOfTravel, chartStatus, ticket.getPassengers()).getUI());
                    frame.pack();
                    frame.setIconImage(Helper.smallIconForFrame);
                    frame.setResizable(false);
                    frame.setVisible(true);
                });
                enablePnrPrompt();
            } else {
                log.warn("THIS PART SHALL NOT BE REACHED EVER");
                showErrorMsgDialog(independentForJOptionPane, "PNR record doesn't exist!");
            }
        } catch (Exception e) {
            log.info("Exception in the reading part... ", e);
            enablePnrPrompt();

            log.error("No reading of webpage as connection not ok! Error : ", e);
            showErrorMsgDialog(independentForJOptionPane, "No Internet Connectivity / The server is down");
        }
    }

    private void enablePnrPrompt() {
        pnrPrompt.setDisableAll(false);
        pnrPrompt.performEnabling();
    }
}