package com.airlines.models.CheckIn;

import lombok.Data;


@Data
public class BaggageIdRequest {
private String customerId;
private String flightNumber;
private String requestId; // To track the request
}