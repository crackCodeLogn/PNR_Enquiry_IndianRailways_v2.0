
package com.vv.PNR_Enquiry_IndianRailways;

import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Prompt;
import com.vv.PNR_Enquiry_IndianRailways.util.Helper;

import javax.swing.*;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-05-2018
 * @since 01-08-2017
 */
public class MainActivity {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            new PNR_Prompt(frame);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setIconImage(Helper.smallIconForFrame);
            frame.setVisible(true);
        });
    }
}