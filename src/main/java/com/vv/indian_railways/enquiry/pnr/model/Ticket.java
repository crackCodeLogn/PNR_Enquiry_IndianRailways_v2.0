package com.vv.indian_railways.enquiry.pnr.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author Vivek
 * @since 04/12/21
 */
@Getter
@Setter
public class Ticket {

    private String pnr;
    private String trainNumber;
    private String trainName;

    private String departStationCode;
    private Long scheduledDepartTime;
    private Long departTime;
    private String boardingStationName;
    private String boardingStationCode;
    private Long scheduledBoardTime;
    private String arriveStationCode;
    private Long scheduledArriveTime;
    private String deboardingStationName;
    private String deboardingStationCode;
    private Long scheduledDeboardTime;
    private String fare;
    private String fareClass;
    private String trainType;
    private boolean chartPrepared;

    private List<Passenger> passengers;

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("pnr", pnr)
                .append("trainNumber", trainNumber)
                .append("trainName", trainName)
                .append("departStationCode", departStationCode)
                .append("scheduledDepartTime", scheduledDepartTime)
                .append("departTime", departTime)
                .append("boardingStationName", boardingStationName)
                .append("boardingStationCode", boardingStationCode)
                .append("scheduledBoardTime", scheduledBoardTime)
                .append("arriveStationCode", arriveStationCode)
                .append("scheduledArriveTime", scheduledArriveTime)
                .append("deboardingStationName", deboardingStationName)
                .append("deboardingStationCode", deboardingStationCode)
                .append("scheduledDeboardTime", scheduledDeboardTime)
                .append("fare", fare)
                .append("fareClass", fareClass)
                .append("trainType", trainType)
                .append("chartPrepared", chartPrepared)
                .append("passengers", passengers)
                .toString();
    }
}