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
public class CurrentBookingStatus {

    private String code;
    private String type;
    private String text;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("code", code)
                .append("type", type)
                .append("text", text)
                .toString();
    }
}