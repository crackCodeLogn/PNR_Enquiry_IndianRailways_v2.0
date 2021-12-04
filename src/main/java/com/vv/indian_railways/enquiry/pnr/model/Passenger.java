package com.vv.indian_railways.enquiry.pnr.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Vivek
 * @since 04/12/21
 */
@Getter
@Setter
@Builder
public class Passenger {

    private String name;
    private String status;
    private String bookingBerthNo;
    private String bookingCoachId;
    private String currentBerthNo;
    private String currentCoachId;
    private CurrentBookingStatus currentBookingStatus;
    private BookingStatus bookingStatus;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("name", name)
                .append("status", status)
                .append("bookingBerthNo", bookingBerthNo)
                .append("bookingCoachId", bookingCoachId)
                .append("currentBerthNo", currentBerthNo)
                .append("currentCoachId", currentCoachId)
                .append("bookingStatus", bookingStatus)
                .append("currentBookingStatus", currentBookingStatus)
                .toString();
    }
}