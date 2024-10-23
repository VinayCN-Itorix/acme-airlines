package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class SeatChangeRequest {
private String customerId;
private String flightNumber;
private String newSeatNumber;
private String requestId; // To track the request
}