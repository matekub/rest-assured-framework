package org.example.apis;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.ApiPaths;
import org.example.http.BaseApi;
import org.example.pojo.request.CreateBookingRequest;

import java.util.Map;

public class UpdateBookingApi extends BaseApi {

    public UpdateBookingApi() {
        super();
        super
//                .logAllRequestData()
//                .logAllResponseData()
                .setContentType(ContentType.JSON);
    }

    public Response updateBooking(Map<String, Object> createBookingPayload, int bookingId,
                                  String username, String password) {
        return getUpdateBookingApiResponse(createBookingPayload, bookingId, username, password);
    }

    public Response updateNewBooking(CreateBookingRequest createBookingRequest, int bookingId,
                                     String username, String password) {
        return getUpdateBookingApiResponse(createBookingRequest, bookingId, username, password);
    }

    private Response getUpdateBookingApiResponse(Object createBookingPayload, int bookingId,
                                                 String username, String password) {
        super.setBasePath(ApiPaths.UPDATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        super.setPathParam("bookingId", bookingId);
        super.setBasicAuth(username, password);
        return super.sendRequest(ApiPaths.UPDATE_BOOKING.getHttpMethodType());
    }
}
