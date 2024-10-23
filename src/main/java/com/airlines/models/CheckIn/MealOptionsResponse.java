package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class MealOptionsResponse {
private boolean successful;
private String message;
private String requestId;
private String mealId;
}
