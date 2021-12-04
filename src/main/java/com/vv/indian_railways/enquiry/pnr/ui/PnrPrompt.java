package com.vv.indian_railways.enquiry.pnr.ui;

import com.vv.indian_railways.enquiry.pnr.remote.PnrEnquirer;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.vv.indian_railways.enquiry.pnr.constants.Constants.*;
import static com.vv.indian_railways.enquiry.pnr.util.Helper.showErrorMsgDialog;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-12-2021
 * @since 04-05-2019
 */
@Slf4j
public class PnrPrompt extends JFrame {

    private static final String SEARCH = "SEARCH";
    private static final String EXIT = "EXIT";
    private static final String RESET = "RESET";
    private static final String TITLE_PNR_ENQUIRER = "PNR Enquiry";
    private static final String TEXT_PNR_PROMPT = "Enter the PNR ";

    private final JTextField textPNR;
    private final JButton buttonSearch;
    private final JButton buttonExit;
    private final JButton buttonReset;
    private boolean disableAll = false;

    public PnrPrompt(JFrame frame) {
        textPNR = new JTextField(PNR_ENTRY_NUM_COLUMNS);
        frame.setTitle(TITLE_PNR_ENQUIRER);

        FlowLayout flowLayout = new FlowLayout();
        frame.setLayout(flowLayout);

        final JLabel displayPNR = new JLabel(TEXT_PNR_PROMPT);
        frame.add(displayPNR);

        frame.add(textPNR);

        buttonSearch = new JButton(SEARCH);
        buttonSearch.addActionListener(e -> {
            final String pnr = textPNR.getText();
            if (!pnr.isEmpty()) {
                if (isPnrValid(pnr)) {
                    try {
                        if (!isDisableAll()) {
                            new Thread(() -> {
                                setDisableAll(true);
                                performDisabling();
                                try {
                                    new PnrEnquirer(this).getPnrDetailAndDisplay(pnr); //modified
                                } catch (Exception e1) {
                                    log.error("Error in the calling for PNR https enquiry : ", e1);
                                }
                            }).start();
                        }
                    } catch (Exception e1) {
                        log.error("Exception occurred in outer thread gen : ", e1);
                    }
                } else {
                    log.warn("Invalid PNR!");
                    showErrorMsgDialog(this, "Invalid PNR!!");
                }
            } else {
                log.warn("Empty!");
                showErrorMsgDialog(this, "PNR length can't be 0!!");
            }
        });
        frame.add(buttonSearch);

        buttonExit = new JButton(EXIT);
        buttonExit.addActionListener(e -> System.exit(ZERO));
        frame.add(buttonExit);

        buttonReset = new JButton(RESET);
        buttonReset.addActionListener(e -> textPNR.setText(EMPTY));
        frame.add(buttonReset);
    }

    public boolean isDisableAll() {
        return disableAll;
    }

    public void setDisableAll(boolean disableAll) {
        this.disableAll = disableAll;
    }

    /**
     * Switches on the interface, so that the next input can be taken
     */
    public void performEnabling() {
        log.info("Enabling all");
        textPNR.setEnabled(true);

        if (buttonSearch != null) buttonSearch.setEnabled(true);
        if (buttonExit != null) buttonExit.setEnabled(true);
        if (buttonReset != null) buttonReset.setEnabled(true);
    }

    /**
     * Locking up the interface whilst the request is being processed
     */
    private void performDisabling() {
        log.info("Disabling all");
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
    boolean isPnrValid(String pnr) {
        log.info("PNR Received for validation : {}", pnr);
        boolean isValidPNR = false;
        if (Objects.nonNull(pnr) && pnr.length() == PNR_LENGTH) {
            int i;
            for (i = 0; i < pnr.length(); i++) if (!Character.isDigit(pnr.charAt(i))) break;
            if (i == 10) isValidPNR = true;
        }
        return isValidPNR;
    }
}