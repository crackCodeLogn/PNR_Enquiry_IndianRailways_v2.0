package com.vv.PNR_Enquiry_IndianRailways.GUI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.*;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
@RunWith(MockitoJUnitRunner.class)
public class PNR_PromptTest {

    @Mock
    JFrame jFrame;
    private PNR_Prompt pnr_prompt;

    @Before
    public void before() {
        pnr_prompt = new PNR_Prompt(jFrame);
    }

    @Test
    public void testIsPNR_Valid() {
        Assert.assertTrue(pnr_prompt.isPNR_Valid("1234567890"));
        Assert.assertTrue(pnr_prompt.isPNR_Valid(String.valueOf((long) (Math.random() * Math.pow(10, 10)))));

        Assert.assertFalse(pnr_prompt.isPNR_Valid(""));
        Assert.assertFalse(pnr_prompt.isPNR_Valid(null));
        Assert.assertFalse(pnr_prompt.isPNR_Valid("123"));
        Assert.assertFalse(pnr_prompt.isPNR_Valid("123456789F"));
        Assert.assertFalse(pnr_prompt.isPNR_Valid("12v456789F"));
        Assert.assertFalse(pnr_prompt.isPNR_Valid("ABCDEFGHIJ"));
        Assert.assertFalse(pnr_prompt.isPNR_Valid("aBCDvFGHIJ"));
        Assert.assertFalse(pnr_prompt.isPNR_Valid("aBCDvFGHIJ"));
    }
}