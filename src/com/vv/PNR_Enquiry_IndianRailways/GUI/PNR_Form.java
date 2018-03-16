package com.vv.PNR_Enquiry_IndianRailways.GUI;

import com.vv.PNR_Enquiry_IndianRailways.MapOfClassOfTravel;
import com.vv.PNR_Enquiry_IndianRailways.Model.*;
//import com.vv.PNR_Enquiry_IndianRailways.Model.PassengerList;

import javax.swing.*;
import java.awt.*;

/**
 * the gridy and gridx distribution:-
 * the gridy -> is for the row number of the cell in grid
 * the gridx -> id for the column number of the cell in the grid
 * <p>
 * the weighty -> vertical spacing
 * the weigthx -> horizontal spacing
 * <p>
 * insets -> for the border of the components. indicates the space that a container must leave at its edges.
 *
 * This is the main output displayer, the final result GUI
 *
 * @author Vivek
 * @version 1.0
 * @since 04-08-2017
 */
public class PNR_Form {

    private JPanel mainPanel;

    public PNR_Form(String pnr, String trainNumber, String trainName, String boardingStation, String destinationStation, String boardingDate, String classOfTravel, String chartStatus, PassengerList listOfPassengers) {
        //super(new GridBagLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel displayPNR = new JLabel("PNR");
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

        JLabel displayTrainNumber = new JLabel("Train Number");
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

        /*
        JLabel null1 = new JLabel();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 60, 0, 60);
        gbc.weightx = 1;
        mainPanel.add(null1, gbc);
        */

        JLabel displayTrainName = new JLabel("Train Name");
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

        JLabel displaySRC = new JLabel("From");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 8, 0);
        gbc.weightx = 1;
        displaySRC.setText(boardingStation);
        mainPanel.add(displaySRC, gbc);

        JLabel displayTo = new JLabel("To");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 8, 20);
        gbc.weightx = 1;
        mainPanel.add(displayTo, gbc);

        JLabel displayDest = new JLabel("Dest");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.EAST;
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 8, 50);
        gbc.weightx = 1;
        displayDest.setText(destinationStation);
        mainPanel.add(displayDest, gbc);

        JLabel displayBoardingDate = new JLabel("Boarding Date");
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

        JLabel displayClass = new JLabel("Class");
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
        //gbc.insets = new Insets(5, 10, 8, 10);
        gbc.insets = new Insets(5, 10, 8, 200);
        gbc.weightx = 1;
        displayValueClass.setEditable(false);
        //classOfTravel+=" -- "+MapOfClassOfTravel.getMap().get(classOfTravel);
        try {
            classOfTravel = MapOfClassOfTravel.getMap().get(classOfTravel);
        } catch (NullPointerException e) {
            //in case the class of travel is not found in the map, then keep it as it is
        }
        displayValueClass.setText(classOfTravel);
        mainPanel.add(displayValueClass, gbc);

        JLabel displayChart = new JLabel("Chart");
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
        System.out.println("List of passengers from the gui part : " + sizeOfListOfPassengers);
        String rows[][] = new String[sizeOfListOfPassengers][3];
        for (int i = 0; i < sizeOfListOfPassengers; i++) {
            rows[i][0] = String.valueOf(listOfPassengers.getListOfPassengers().get(i).getNumber());
            if(listOfPassengers.getListOfPassengers().get(i).getBookingStatus()!=null)
                rows[i][1] = listOfPassengers.getListOfPassengers().get(i).getBookingStatus();
            if(listOfPassengers.getListOfPassengers().get(i).getCurrentStatus()!=null)
                rows[i][2] = listOfPassengers.getListOfPassengers().get(i).getCurrentStatus();
        }

        String columns[] = {"Number", "Booking Status", "Current Status"};
        JTable displayTable = new JTable(rows, columns);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        Dimension d = displayTable.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, displayTable.getRowHeight() * (sizeOfListOfPassengers + 3)));

        //displayTable.setBounds(30, 40, 100, 20);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.weightx = 1;
        mainPanel.add(scrollPane, gbc);

        /*
        JLabel lblAddress = new JLabel("Address");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        mainPanel.add(lblAddress, gbc);

        JTextArea txtAreaAddress = new JTextArea(10, 10);
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 0, 10);
        gbc.weightx = 1;
        mainPanel.add(txtAreaAddress, gbc);
        */
    }

    public JPanel getUI() {
        return mainPanel;
    }

    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("PNR sample product");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().add(new PNR_Form().getUI());

                // Create and set up the content pane.
                /*
                JComponent newContentPane = new PNR_Sample();
                newContentPane.setOpaque(true); // content panes must be opaque
                frame.setContentPane(newContentPane);

                // Display the window.
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }
    */
}