package com.vv.indian_railways.enquiry.pnr.remote.feign;

import com.vv.indian_railways.enquiry.pnr.model.TicketData;
import feign.Param;
import feign.RequestLine;

/**
 * @author Vivek
 * @since 04/12/21
 */
public interface PnrEnquiryClient {

    @RequestLine("GET /v2/pnr?pnr={pnr}")
    TicketData getTicketDetail(@Param("pnr") String pnr);
}