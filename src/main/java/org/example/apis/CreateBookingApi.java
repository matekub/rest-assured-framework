package org.example.apis;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.ApiPaths;
import org.example.http.BaseApi;
import org.example.pojo.request.CreateBookingRequest;

import java.util.Map;

public class CreateBookingApi extends BaseApi{

    public CreateBookingApi() {
        super();
        super.logAllRequestData().logAllResponseData().setContentType(ContentType.JSON);
    }

    public Response createNewBooking(Map<String, Object> createBookingPayload) {
        return getCreateBookingApiResponse(createBookingPayload);
    }

    public Response createNewBooking(CreateBookingRequest createBookingRequest) {
        return getCreateBookingApiResponse(createBookingRequest);
    }

    private Response getCreateBookingApiResponse(Object createBookingPayload) {
        super.setBasePath(ApiPaths.CREATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        return super.sendRequest(ApiPaths.CREATE_BOOKING.getHttpMethodType());
    }
}
