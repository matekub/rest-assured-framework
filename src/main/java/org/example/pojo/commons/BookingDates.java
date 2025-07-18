package org.example.pojo.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingDates {

	@JsonProperty("checkin")
	private String checkIn;

	@JsonProperty("checkout")
	private String checkOut;
}