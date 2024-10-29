package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class StitchRequestBody {
private String requestId; // From TravelDetails
private String seatNumber; // From SeatChange
private String baggageId; // From BaggageId
private String mealOption; // From MealOptions
private String boardingId;
private boolean success;
}
