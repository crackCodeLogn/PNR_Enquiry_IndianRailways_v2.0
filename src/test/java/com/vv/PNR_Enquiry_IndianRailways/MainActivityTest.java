package com.vv.PNR_Enquiry_IndianRailways;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
@RunWith(JUnit4.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void before() {
        mainActivity = new MainActivity();
    }

    @Test
    public void testIsPNR_Valid() {
        Assert.assertTrue(mainActivity.isPNR_Valid("1234567890"));
        Assert.assertTrue(mainActivity.isPNR_Valid(String.valueOf((long) (Math.random() * Math.pow(10, 10)))));

        Assert.assertFalse(mainActivity.isPNR_Valid(""));
        Assert.assertFalse(mainActivity.isPNR_Valid(null));
        Assert.assertFalse(mainActivity.isPNR_Valid("123"));
        Assert.assertFalse(mainActivity.isPNR_Valid("123456789F"));
        Assert.assertFalse(mainActivity.isPNR_Valid("12v456789F"));
        Assert.assertFalse(mainActivity.isPNR_Valid("ABCDEFGHIJ"));
        Assert.assertFalse(mainActivity.isPNR_Valid("aBCDvFGHIJ"));
        Assert.assertFalse(mainActivity.isPNR_Valid("aBCDvFGHIJ"));
    }
}