
package com.vv.PNR_Enquiry_IndianRailways;

import com.vv.PNR_Enquiry_IndianRailways.HttpsAcquirer.PNR_EnquirerHttps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static com.vv.PNR_Enquiry_IndianRailways.util.Constants.*;
import static com.vv.PNR_Enquiry_IndianRailways.util.Helper.showErrorMsgDialog;

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

    private final static Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private static final JTextField textPNR = new JTextField(PNR_ENTRY_NUM_COLUMNS);
    private final FlowLayout FLOW_LAYOUT = new FlowLayout();
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
        logger.info("Enabling all");
        textPNR.setEnabled(true);

        if (buttonSearch != null) buttonSearch.setEnabled(true);
        if (buttonExit != null) buttonExit.setEnabled(true);
        if (buttonReset != null) buttonReset.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainActivity mainActivity = new MainActivity();
            JFrame frame = mainActivity.MainActivity_setUI(new JFrame());
            frame.pack();
            frame.setLocationRelativeTo(frame);
            try {
                Image image = ImageIO.read(MainActivity.class.getResourceAsStream(smallLogoPath));
                frame.setIconImage(image);
            } catch (Exception e1) {
                logger.error("Small logo path not found... Error : ", e1);
            }
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    private JFrame MainActivity_setUI(JFrame frame) {
        frame.setTitle(PNR_ENQUIRER);
        frame.setLayout(FLOW_LAYOUT);

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
                    showErrorMsgDialog(MainActivity.this, "Invalid PNR!!");
                }
            } else {
                logger.warn("Empty!");
                showErrorMsgDialog(MainActivity.this, "PNR length can't be 0!!");
            }
        });
        frame.add(buttonSearch);

        buttonExit = new JButton(EXIT);
        buttonExit.addActionListener(e -> System.exit(ZERO));
        frame.add(buttonExit);

        buttonReset = new JButton(RESET);
        buttonReset.addActionListener(e -> textPNR.setText(EMPTY_STR));
        frame.add(buttonReset);
        return frame;
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
}