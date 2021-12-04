
package com.vv.indian_railways.enquiry.pnr;

import com.vv.indian_railways.enquiry.pnr.ui.PnrPrompt;
import com.vv.indian_railways.enquiry.pnr.util.Helper;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-12-2021
 * @since 01-08-2017
 */
@Slf4j
public class MainActivity {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            new PnrPrompt(frame);

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setIconImage(Helper.smallIconForFrame);

            log.info("**** Launching PNR GUI now ****");
            frame.setVisible(true);
        });
    }
}