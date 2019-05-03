package com.vv.PNR_Enquiry_IndianRailways;

import com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer.PNR_EnquirerHttps;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 16-03-2018
 * @lastMod_details -> Changes made in order to accomodate the usage of checker framework's annotations
 * -> Changing the entire calling procedure for the initial GUI, i.e. passing and returning JFrame in MainActivity_setUI which was
 * originally a normal constructor which was called by the Jframe and the gui was set up
 * -> line 68 modified, as the PNR_EnquirerHttps class underwent a structural change
 * @since 01-08-2017
 */
public class MainActivity extends JFrame {

    //public final static String smallLogoPath = "/src/main/java/com/vv/PNR_Enquiry_IndianRailways/raw/logoIR_medium256.jpg";
    //public final static String smallLogoPath = "/com/vv/PNR_Enquiry_IndianRailways/raw/logoIR_medium256.jpg";
    ////public final static String smallLogoPath = "/logoIR_medium256.jpg"; //image in the resources folder of the project
    //public final static String smallLogoPath = "/logoIR_full1024.png"; //image in the resources folder of the project
    public final static String smallLogoPath = "/resources/logoIR_small32.png"; //image in the resources folder of the project
    public final static String mediumLogoPath = "/resources/logoIR_medium256.png"; //image in the resources folder of the project
    public final static String bigLogoPath = "/resources/logoIR_full1024.png"; //image in the resources folder of the project
    private static final JTextField textPNR = new JTextField(10);
    public static boolean disableAll = false;
    @Nullable
    private static JButton buttonSearch = null;
    @Nullable
    private static JButton buttonExit = null;
    @Nullable
    private static JButton buttonReset = null;

    /**
     * Switches on the interface, so that the next input can be taken
     */
    public static void performEnabling() {
        textPNR.setEnabled(true);

        if (buttonSearch != null) buttonSearch.setEnabled(true);
        if (buttonExit != null) buttonExit.setEnabled(true);
        if (buttonReset != null) buttonReset.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //JFrame frame = new MainActivity();
                MainActivity a1 = new MainActivity();
                JFrame frame = new JFrame();
                frame = a1.MainActivity_setUI(frame);
                frame.setVisible(true);
                frame.pack();
                //frame.setLocationRelativeTo(null);
                frame.setLocationRelativeTo(new JFrame());
                try {
                    frame.setIconImage(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainActivity.class.getResource(smallLogoPath))).getImage());
                } catch (Exception npe1) {
                    //the image was not found on the file path
                }
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

    public JFrame MainActivity_setUI(JFrame frame) {
        frame.setTitle("PNR Enquirer");
        frame.setLayout(new FlowLayout());

        final JLabel displayPNR = new JLabel("Enter the PNR ");
        frame.add(displayPNR);

        //textPNR = new JTextField(10);
        frame.add(textPNR);

        buttonSearch = new JButton("SEARCH");
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String pnr = textPNR.getText();
                if (pnr.length() != 0) {
                    if (isPNR_Valid(pnr)) {
                        try {
                            if (!disableAll) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        disableAll = true;
                                        performDisabling();
                                        //new PNR_EnquirerHttps(pnr);
                                        try {
                                            new PNR_EnquirerHttps().PNR_EnquirerHttpsRunner(pnr); //modified
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                            /*
                            if(!disableAll){
                                disableAll = true;
                                performDisabling();
                                //PNR_EnquirerHttps.PNR_EnquirerHttpsRunner(pnr);
                                new PNR_EnquirerHttps(pnr);

                                /*
                                while(PNR_EnquirerHttps.loopON){

                                }

                            }
                            */
                        } catch (Exception e1) {
                            System.out.println("Exception occured : " + e1);
                        }
                    } else {
                        System.out.println("Invalid PNR!");
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(MainActivity.this, "Invalid PNR!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.out.println("Empty!");
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(MainActivity.this, "PNR length can't be 0!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.add(buttonSearch);

        buttonExit = new JButton("EXIT");
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.add(buttonExit);

        buttonReset = new JButton("RESET");
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPNR.setText("");
            }
        });
        frame.add(buttonReset);
        return frame;
    }

    /**
     * Verifies whether the input PNR is valid or not
     *
     * @param pnr
     * @return
     */
    private boolean isPNR_Valid(String pnr) {
        boolean isValid = false;
        if (pnr.length() == 10) {
            int i;
            for (i = 0; i < pnr.length(); i++) {
                char ch = pnr.charAt(i);
                if (!Character.isDigit(ch)) break;
            }
            if (i == 10) isValid = true;
        }
        return isValid;
    }

    /**
     * Locking up the interface whilst the request is being processed
     */
    public void performDisabling() {
        System.out.println("Inside the disabling function");
        textPNR.setEnabled(false);
        if (buttonSearch != null) buttonSearch.setEnabled(false);
        if (buttonExit != null) buttonExit.setEnabled(false);
        if (buttonReset != null) buttonReset.setEnabled(false);
    }
}