package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class TravelDetailsRequest {
private String customerId;
private String flightNumber;
private String travelDate;
private String requestId;
private String seatNo;
private String mealId;
private boolean extraLuggage;
}
