package com.vv.indian_railways.enquiry.pnr.util;

import com.vv.indian_railways.enquiry.pnr.MainActivity;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.vv.indian_railways.enquiry.pnr.constants.Constants.SMALL_LOGO_PATH;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-12-2021
 * @since 04-05-2019
 */
@Slf4j
public class Helper {
    private static final String ERROR = "Error";
    public static Image smallIconForFrame = null;

    static {
        try {
            smallIconForFrame = ImageIO.read(MainActivity.class.getResourceAsStream(SMALL_LOGO_PATH));
        } catch (IOException e) {
            log.error("Small logo path not found... Error : ", e);
        }
    }

    Helper() {
    }

    public static void showErrorMsgDialog(Component parentComponent, String message) {
        try {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(parentComponent, message, ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            log.error("Error encountered whilst showing error message dialog : ", e);
        }
    }
}
