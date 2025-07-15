package org.example.apis;

import io.restassured.response.Response;
import org.example.http.BaseApi;
import org.slf4j.Logger;

import static org.example.constants.ApiPaths.DELETE_BOOKING;

public class DeleteBookingApi extends BaseApi {

    Logger logger = org.slf4j.LoggerFactory.getLogger(DeleteBookingApi.class);

    public DeleteBookingApi() {
        super();
        super.logAllRequestData().logAllResponseData();
    }

    public Response deleteBookingById(int bookingId, String username, String password){
        super.setBasePath(DELETE_BOOKING.getApiPath());
        super.setPathParam("bookingId", bookingId);
        super.setBasicAuth(username, password);
        logger.info("Deleting booking with id: {}", bookingId);
        return super.sendRequest(DELETE_BOOKING.getHttpMethodType());
    }
}
