package com.vv.PNR_Enquiry_IndianRailways.util;

import com.vv.PNR_Enquiry_IndianRailways.MainActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.ERROR;
import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.smallLogoPath;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
public class Helper {

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    public static Image smallIconForFrame = null;

    static {
        try {
            smallIconForFrame = ImageIO.read(MainActivity.class.getResourceAsStream(smallLogoPath));
        } catch (IOException e) {
            logger.error("Small logo path not found... Error : ", e);
        }
    }

    public static void showErrorMsgDialog(Component parentComponent, String message) {
        try {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(parentComponent, message, ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error encountered whilst showing error message dialog : ", e);
        }
    }
}
