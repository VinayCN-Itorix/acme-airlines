package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class RejectedCheckinsRequest {
private String requestId;      // Unique identifier for the request
private String flightNumber;    // Flight number for the check-in
private String passengerId;     // ID of the passenger
private String reason;           // Reason for the rejection
}