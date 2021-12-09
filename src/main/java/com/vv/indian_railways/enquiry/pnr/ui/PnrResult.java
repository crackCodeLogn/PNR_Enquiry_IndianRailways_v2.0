package com.vv.indian_railways.enquiry.pnr.ui;

import com.vv.indian_railways.enquiry.pnr.model.Passenger;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.vv.indian_railways.enquiry.pnr.util.MapOfClassOfTravel.TRAVEL_CLASS_MAP;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * the gridy and gridx distribution:-
 * the gridy -> is for the row number of the cell in grid
 * the gridx -> id for the column number of the cell in the grid
 * <p>
 * the weighty -> vertical spacing
 * the weigthx -> horizontal spacing
 * <p>
 * insets -> for the border of the components. indicates the space that a container must leave at its edges.
 * <p>
 * This is the main output displayer, the final result GUI
 *
 * @author Vivek
 * @version 1.0
 * @lastMod 04-12-2021
 * @since 04-08-2017
 */
@Slf4j
public class PnrResult {

    private final JPanel mainPanel = new JPanel();
    private final String[] columns = {"Number", "Booking Status", "Current Status"};

    private static final String CHART = "Chart";
    private static final String PNR = "PNR";
    private static final String TRAIN_NUMBER = "Train Number";
    private static final String TRAIN_NAME = "Train Name";
    private static final String FROM = "From";
    private static final String TO = "To";
    private static final String DEST = "Dest";
    private static final String BOARDING_DATE = "Boarding Date";
    private static final String CLASS = "Class";
    private static final String FARE = "Fare";
    private static final String RAKE = "Rake";

    public PnrResult(String pnr, String trainNumber, String trainName, String boardingStation, String destinationStation, String boardingDate, String classOfTravel, String chartStatus,
                     String fare, String rake, List<Passenger> passengerList) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        mainPanel.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel displayPNR = new JLabel(PNR);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        mainPanel.add(displayPNR, gbc);

        JTextField displayValuePNR = new JTextField(20);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 0, 200);
        gbc.weightx = 1;
        displayValuePNR.setEditable(false);
        displayValuePNR.setText(pnr);
        mainPanel.add(displayValuePNR, gbc);

        JLabel displayTrainNumber = new JLabel(TRAIN_NUMBER);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        mainPanel.add(displayTrainNumber, gbc);

        JTextField displayValueTrainNumber = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 0, 200);
        gbc.weightx = 1;
        displayValueTrainNumber.setEditable(false);
        displayValueTrainNumber.setText(trainNumber);
        mainPanel.add(displayValueTrainNumber, gbc);

        JLabel displayTrainName = new JLabel(TRAIN_NAME);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 8, 0);
        gbc.weightx = 1;
        mainPanel.add(displayTrainName, gbc);

        JTextField displayValueTrainName = new JTextField(20);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3; //previously this value was set to 1, leading to not proper width of this component
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.weightx = 1;
        displayValueTrainName.setEditable(false);
        displayValueTrainName.setText(trainName);
        mainPanel.add(displayValueTrainName, gbc);

        JLabel displaySRC = new JLabel(FROM);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 8, 0);
        gbc.weightx = 1;
        displaySRC.setText(boardingStation);
        mainPanel.add(displaySRC, gbc);

        JLabel displayTo = new JLabel(TO);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 8, 20);
        gbc.weightx = 1;
        mainPanel.add(displayTo, gbc);

        JLabel displayDest = new JLabel(DEST);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.EAST;
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 8, 50);
        gbc.weightx = 1;
        displayDest.setText(destinationStation);
        mainPanel.add(displayDest, gbc);

        JLabel displayBoardingDate = new JLabel(BOARDING_DATE);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        mainPanel.add(displayBoardingDate, gbc);

        JTextField displayValueBoardingDate = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 0, 200);
        gbc.weightx = 1;
        displayValueBoardingDate.setEditable(false);
        displayValueBoardingDate.setText(boardingDate);
        mainPanel.add(displayValueBoardingDate, gbc);

        JLabel displayClass = new JLabel(CLASS);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 0, 0);
        gbc.weightx = 1;
        mainPanel.add(displayClass, gbc);

        JTextField displayValueClass = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 0, 200);
        gbc.weightx = 1;
        displayValueClass.setEditable(false);

        classOfTravel = TRAVEL_CLASS_MAP.getOrDefault(classOfTravel, EMPTY);
        displayValueClass.setText(classOfTravel);
        mainPanel.add(displayValueClass, gbc);

        JLabel displayChart = new JLabel(CHART);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, -80, 0, 0);
        gbc.weightx = 1;
        mainPanel.add(displayChart, gbc);

        JTextField displayValueChart = new JTextField(15);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, -30, 0, 10);
        gbc.weightx = 1;
        displayValueChart.setEditable(false);
        displayValueChart.setText(chartStatus);
        mainPanel.add(displayValueChart, gbc);

        JLabel displayFare = new JLabel(FARE);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 8, 0);
        gbc.weightx = 1;
        mainPanel.add(displayFare, gbc);

        JTextField displayValueFare = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 8, 200);
        gbc.weightx = 1;
        displayValueFare.setEditable(false);
        displayValueFare.setText(fare);
        mainPanel.add(displayValueFare, gbc);

        JLabel displayRake = new JLabel(RAKE);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, -80, 8, 0);
        gbc.weightx = 1;
        mainPanel.add(displayRake, gbc);

        JTextField displayValueRake = new JTextField(15);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, -30, 8, 10);
        gbc.weightx = 1;
        displayValueRake.setEditable(false);
        displayValueRake.setText(rake);
        mainPanel.add(displayValueRake, gbc);

        /*String rows[][] = {{"1", "B2", "B2"},
                             {"2", "B3", "B3"}};*/
        int sizeOfListOfPassengers = passengerList.size();
        String[][] rows = new String[sizeOfListOfPassengers][3];
        for (int i = 0; i < sizeOfListOfPassengers; i++) {
            rows[i][0] = String.valueOf(i + 1);
            Passenger passenger = passengerList.get(i);
            rows[i][1] = String.format("%s,%s,%s", passenger.getBookingCoachId(), passenger.getBookingBerthNo(), passenger.getBookingStatus().getCode());
            rows[i][2] = String.format("%s,%s,%s", passenger.getCurrentCoachId(), passenger.getCurrentBerthNo(), passenger.getCurrentBookingStatus().getCode());
        }

        JTable displayTable = new JTable(rows, columns);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        Dimension d = displayTable.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, displayTable.getRowHeight() * (sizeOfListOfPassengers + 3)));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.weightx = 1;
        mainPanel.add(scrollPane, gbc);
    }

    public JPanel getUI() {
        return mainPanel;
    }
}