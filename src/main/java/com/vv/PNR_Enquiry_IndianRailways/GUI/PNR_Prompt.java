package com.vv.PNR_Enquiry_IndianRailways.GUI;

import com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer.PNR_EnquirerHttps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.*;
import static com.vv.PNR_Enquiry_IndianRailways.util.Helper.showErrorMsgDialog;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
public class PNR_Prompt extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(PNR_Prompt.class);

    private static final JTextField textPNR = new JTextField(PNR_ENTRY_NUM_COLUMNS);
    public static boolean disableAll = false;
    @Nullable
    private static JButton buttonSearch = null;
    @Nullable
    private static JButton buttonExit = null;
    @Nullable
    private static JButton buttonReset = null;
    private final FlowLayout flowLayout = new FlowLayout();

    public PNR_Prompt(JFrame frame) {
        frame.setTitle(PNR_ENQUIRER);
        frame.setLayout(flowLayout);

        final JLabel displayPNR = new JLabel(TEXT_PNR_PROMPT);
        frame.add(displayPNR);

        frame.add(textPNR);

        buttonSearch = new JButton(SEARCH);
        buttonSearch.addActionListener(e -> {
            final String pnr = textPNR.getText();
            if (pnr.length() != 0) {
                if (isPNR_Valid(pnr)) {
                    try {
                        if (!disableAll) {
                            new Thread(() -> {
                                disableAll = true;
                                performDisabling();
                                try {
                                    new PNR_EnquirerHttps().PNR_EnquirerHttpsRunner(pnr); //modified
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }).start();
                        }
                    } catch (Exception e1) {
                        logger.error("Exception occurred : ", e1);
                    }
                } else {
                    logger.warn("Invalid PNR!");
                    showErrorMsgDialog(this, "Invalid PNR!!");
                }
            } else {
                logger.warn("Empty!");
                showErrorMsgDialog(this, "PNR length can't be 0!!");
            }
        });
        frame.add(buttonSearch);

        buttonExit = new JButton(EXIT);
        buttonExit.addActionListener(e -> System.exit(ZERO));
        frame.add(buttonExit);

        buttonReset = new JButton(RESET);
        buttonReset.addActionListener(e -> textPNR.setText(EMPTY_STR));
        frame.add(buttonReset);
    }

    /**
     * Switches on the interface, so that the next input can be taken
     */
    public static void performEnabling() {
        logger.info("Enabling all");
        textPNR.setEnabled(true);

        if (buttonSearch != null) buttonSearch.setEnabled(true);
        if (buttonExit != null) buttonExit.setEnabled(true);
        if (buttonReset != null) buttonReset.setEnabled(true);
    }

    /**
     * Locking up the interface whilst the request is being processed
     */
    private void performDisabling() {
        logger.info("Disabling all");
        textPNR.setEnabled(false);

        if (buttonSearch != null) buttonSearch.setEnabled(false);
        if (buttonExit != null) buttonExit.setEnabled(false);
        if (buttonReset != null) buttonReset.setEnabled(false);
    }

    /**
     * Verifies whether the input PNR is valid or not
     *
     * @param pnr
     * @return boolean value stating the validity of the pnr
     */
    boolean isPNR_Valid(String pnr) {
        logger.info("PNR Received for validation : {}", pnr);
        boolean isValidPNR = false;
        if (Objects.nonNull(pnr) && pnr.length() == PNR_LENGTH) {
            int i;
            for (i = 0; i < pnr.length(); i++) if (!Character.isDigit(pnr.charAt(i))) break;
            if (i == 10) isValidPNR = true;
        }
        return isValidPNR;
    }

}
