package com.vv.indian_railways.enquiry.pnr.ui;

import com.vv.indian_railways.enquiry.pnr.MainActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vivek
 * @version 1.0
 * @since 04-05-2019
 */
@ExtendWith(MockitoExtension.class)
class Pnr_PromptTest extends ForGUI_Test {

    @Mock
    JFrame jFrame;

    private PnrPrompt pnr_prompt;

    @BeforeEach
    public void before() {
        pnr_prompt = new PnrPrompt(jFrame);
    }

    @Test
    void testIsPNR_Valid() {
        assertThat(pnr_prompt.isPnrValid("1234567890")).isTrue();
        assertThat(pnr_prompt.isPnrValid(String.valueOf((long) (Math.random() * Math.pow(10, 10))))).isTrue();

        assertThat(pnr_prompt.isPnrValid("")).isFalse();
        assertThat(pnr_prompt.isPnrValid(null)).isFalse();
        assertThat(pnr_prompt.isPnrValid("123")).isFalse();
        assertThat(pnr_prompt.isPnrValid("123456789F")).isFalse();
        assertThat(pnr_prompt.isPnrValid("12v456789F")).isFalse();
        assertThat(pnr_prompt.isPnrValid("ABCDEFGHIJ")).isFalse();
        assertThat(pnr_prompt.isPnrValid("aBCDvFGHIJ")).isFalse();
        assertThat(pnr_prompt.isPnrValid("aBCDvFGHIJ")).isFalse();
    }

    @Test
    void launchUI() {
        MainActivity.main(null);
    }
}