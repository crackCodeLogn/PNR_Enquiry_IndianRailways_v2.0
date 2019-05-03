package com.vv.PNR_Enquiry_IndianRailways;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vivek
 * @version 1.0
 * @lastMod 12-03-2018
 * @since 04-08-2017
 */
public class MapOfClassOfTravel {
    protected static Map<String, String> map = new HashMap<String, String>();

    /*
    public MapOfClassOfTravel(){
        map.put("1A", "AC First Class; 2-berth, 4-berth");
        map.put("2A", "AC 2-Tier; 2-berth, 4-berth");
        map.put("3A", "AC 3-Tier; 2-berth, 6-berth");
        map.put("FC", "First Class; 2-berth, 4-berth");
        map.put("CC", "AC Chair Car; 2-seat, 3-seat");
        map.put("SL", "Sleeper; 2-berth, 6-berth");
        map.put("2S", "Second Seating; -, -");
    }
    */

    public MapOfClassOfTravel() {
        map.put("AC 1-Tier", "1A -- AC First Class; 2-berth, 4-berth");
        map.put("AC 2-tier", "2A -- AC 2-Tier; 2-berth, 4-berth");
        map.put("AC 3 Tier", "3A -- AC 3-Tier; 2-berth, 6-berth");
        //map.put("FC", "First Class; 2-berth, 4-berth");
        map.put("AC chair Car", "CC -- AC Chair Car; 2-seat, 3-seat");
        map.put("Sleeper", "SL -- Sleeper; 2-berth, 6-berth");
        map.put("Second Sitting", "2S -- Second Sitting; -. -");
    }

    public static Map<String, String> getMap() {
        return map;
    }
}
