package org.example.util;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestHelper {

    public static Map<String, Object> getCreateBookingApiRequestBody(String firstName,
                                                                     String lastName,
                                                                     int totalPrice,
                                                                     boolean depositPaid,
                                                                     String additionalNeeds,
                                                                     String checkInDate,
                                                                     String checkOutDate) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", firstName);
        requestBody.put("lastname", lastName);
        requestBody.put("totalprice", totalPrice);
        requestBody.put("depositpaid", depositPaid);
        requestBody.put("additionalneeds", additionalNeeds);

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkInDate);
        bookingDates.put("checkout", checkOutDate);

        requestBody.put("bookingdates", bookingDates);
        return requestBody;
    }
}
