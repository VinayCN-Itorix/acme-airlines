package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class ExtraLuggageRequest {
private String customerId;
private String flightNumber;
private int luggageCount;
private String requestId; // To track the request
}
