package org.example.apis;

import io.restassured.response.Response;
import org.example.http.BaseApi;

import static org.example.constants.ApiPaths.GET_BOOKING;
import static org.example.constants.ApiPaths.GET_BOOKING_IDS;

public class GetBookingApi extends BaseApi {

    public GetBookingApi() {
        super();
        super.logAllRequestData()
                .logAllResponseData();
    }

    public Response getAllBookingsIds(){
        super.setBasePath(GET_BOOKING_IDS.getApiPath());
        return super.sendRequest(GET_BOOKING_IDS.getHttpMethodType());
    }
    public Response getBookingById(int bookingId){
        super.setBasePath(GET_BOOKING.getApiPath());
        super.setPathParam("bookingId", bookingId);
        return super.sendRequest(GET_BOOKING.getHttpMethodType());
    }
}
