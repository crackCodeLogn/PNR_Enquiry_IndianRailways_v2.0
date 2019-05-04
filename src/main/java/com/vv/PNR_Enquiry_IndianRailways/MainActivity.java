
package com.vv.PNR_Enquiry_IndianRailways;

import com.vv.PNR_Enquiry_IndianRailways.GUI.PNR_Prompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.smallLogoPath;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-05-2018
 * @since 01-08-2017
 */
public class MainActivity {

    private final static Logger logger = LoggerFactory.getLogger(MainActivity.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            new PNR_Prompt(frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            try {
                Image image = ImageIO.read(MainActivity.class.getResourceAsStream(smallLogoPath));
                frame.setIconImage(image);
            } catch (Exception e1) {
                logger.error("Small logo path not found... Error : ", e1);
            }
            frame.setVisible(true);
        });
    }
}