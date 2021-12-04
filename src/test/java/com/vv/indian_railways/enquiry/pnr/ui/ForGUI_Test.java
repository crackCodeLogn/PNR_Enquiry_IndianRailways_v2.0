package com.vv.indian_railways.enquiry.pnr.ui;

import org.junit.jupiter.api.AfterEach;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
public abstract class ForGUI_Test {
    private final int TIME_BEFORE_KILLING_TEST_GUI = 2000;

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(TIME_BEFORE_KILLING_TEST_GUI);
    }
}