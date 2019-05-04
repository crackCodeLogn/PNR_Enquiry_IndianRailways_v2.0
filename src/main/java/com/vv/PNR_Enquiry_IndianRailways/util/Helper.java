package com.vv.PNR_Enquiry_IndianRailways.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.ERROR;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
public class Helper {

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    public static void showErrorMsgDialog(Component parentComponent, String message) {
        try {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(parentComponent, message, ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("Error encountered whilst showing error message dialog : ", e);
        }
    }
}
