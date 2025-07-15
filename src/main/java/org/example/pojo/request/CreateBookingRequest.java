package org.example.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.pojo.commons.BookingDates;

@Data
public class CreateBookingRequest{

	@JsonProperty("firstname")
	private String firstName;

	@JsonProperty("lastname")
	private String lastName;

	@JsonProperty("additionalneeds")
	private String additionalNeeds;

	@JsonProperty("bookingdates")
	private BookingDates bookingDates;

	@JsonProperty("totalprice")
	private int totalPrice;

	@JsonProperty("depositpaid")
	private boolean depositPaid;
}