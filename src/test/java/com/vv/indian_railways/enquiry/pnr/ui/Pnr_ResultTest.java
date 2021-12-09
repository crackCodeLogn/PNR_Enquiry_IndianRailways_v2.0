package com.vv.indian_railways.enquiry.pnr.ui;

import com.vv.indian_railways.enquiry.pnr.model.BookingStatus;
import com.vv.indian_railways.enquiry.pnr.model.CurrentBookingStatus;
import com.vv.indian_railways.enquiry.pnr.model.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
@ExtendWith(MockitoExtension.class)
class Pnr_ResultTest extends ForGUI_Test {

    @Test
    void launchUI() {
        Passenger p1 = Passenger.builder()
                .bookingBerthNo("8")
                .bookingStatus(BookingStatus.builder().code("WL").build())
                .currentBookingStatus(CurrentBookingStatus.builder().code("CNF").build())
                .build();
        Passenger p2 = Passenger.builder()
                .bookingBerthNo("8")
                .bookingStatus(BookingStatus.builder().code("WL").build())
                .currentBookingStatus(CurrentBookingStatus.builder().code("CNF").build())
                .build();
        Passenger p3 = Passenger.builder()
                .bookingBerthNo("8")
                .bookingStatus(BookingStatus.builder().code("WL").build())
                .currentBookingStatus(CurrentBookingStatus.builder().code("CNF").build())
                .build();
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(p1);
        passengers.add(p2);
        passengers.add(p3);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PNR sample product");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().add(new PnrResult(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, passengers).getUI());

            // Display the window.
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}