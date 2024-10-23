package com.airlines.models.CheckIn;

import lombok.Data;


@Data
public class MealOptionsRequest {
private String customerId;
private String flightNumber;
private String mealChoice;
private String requestId; // To track the request
}
