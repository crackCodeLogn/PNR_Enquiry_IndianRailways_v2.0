package com.vv.indian_railways.enquiry.pnr.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 04-12-2021
 * @since 04-08-2017
 * <p>
 */
public class MapOfClassOfTravel {
    public static final Map<String, String> TRAVEL_CLASS_MAP = ImmutableMap.<String, String>builder()
            .put("2A", "2A -- AC 2-Tier; 2-berth, 4-berth")
            .put("1A", "1A -- AC First Class; 2-berth, 4-berth")
            .put("3A", "3A -- AC 3-Tier; 2-berth, 6-berth")
            .put("SL", "SL -- Sleeper; 2-berth, 6-berth")
            .put("CC", "CC -- AC Chair Car; 2-seat, 3-seat")
            .put("2S", "2S -- Second Sitting; 2-seat, 3-seat")
            .build();

    MapOfClassOfTravel() {
    }
}