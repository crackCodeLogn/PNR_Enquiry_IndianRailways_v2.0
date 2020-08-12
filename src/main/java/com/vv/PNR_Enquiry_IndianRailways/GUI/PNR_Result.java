package com.vv.PNR_Enquiry_IndianRailways.GUI;

import com.vv.PNR_Enquiry_IndianRailways.Model.PassengerList;
import com.vv.PNR_Enquiry_IndianRailways.util.MapOfClassOfTravel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.*;

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
 * @since 04-08-2017
 */
public class PNR_Result {

    private final Logger logger = LoggerFactory.getLogger(PNR_Result.class);

    private final GridBagLayout gridBagLayout = new GridBagLayout();
    private final JPanel mainPanel = new JPanel();

    private final String[] columns = {"Number", "Booking Status", "Current Status"};

    public PNR_Result(String pnr, String trainNumber, String trainName, String boardingStation, String destinationStation, String boardingDate, String classOfTravel, String chartStatus, PassengerList listOfPassengers, MapOfClassOfTravel mapOfClassOfTravel) {
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
        gbc.insets = new Insets(5, 10, 8, 0);
        gbc.weightx = 1;
        mainPanel.add(displayClass, gbc);

        JTextField displayValueClass = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 8, 200);
        gbc.weightx = 1;
        displayValueClass.setEditable(false);

        classOfTravel = mapOfClassOfTravel.getMap().getOrDefault(classOfTravel, EMPTY_STR);
        displayValueClass.setText(classOfTravel);
        mainPanel.add(displayValueClass, gbc);

        JLabel displayChart = new JLabel(CHART);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, -80, 8, 0);
        gbc.weightx = 1;
        mainPanel.add(displayChart, gbc);

        JTextField displayValueChart = new JTextField(15);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, -30, 8, 10);
        gbc.weightx = 1;
        displayValueChart.setEditable(false);
        displayValueChart.setText(chartStatus);
        mainPanel.add(displayValueChart, gbc);

        //String rows[][] = {{"1", "B2", "B2"},
        //                   {"2", "B3", "B3"}};
        int sizeOfListOfPassengers = listOfPassengers.getListOfPassengers().size();
        logger.info("List of passengers from the gui part : {}", sizeOfListOfPassengers);
        String[][] rows = new String[sizeOfListOfPassengers][3];
        for (int i = 0; i < sizeOfListOfPassengers; i++) {
            rows[i][0] = String.valueOf(listOfPassengers.getListOfPassengers().get(i).getNumber());
            rows[i][1] = listOfPassengers.getListOfPassengers().get(i).getBookingStatus();
            rows[i][2] = listOfPassengers.getListOfPassengers().get(i).getCurrentStatus();
        }

        JTable displayTable = new JTable(rows, columns);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        Dimension d = displayTable.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, displayTable.getRowHeight() * (sizeOfListOfPassengers + 3)));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.weightx = 1;
        mainPanel.add(scrollPane, gbc);
    }

    public JPanel getUI() {
        return mainPanel;
    }

}