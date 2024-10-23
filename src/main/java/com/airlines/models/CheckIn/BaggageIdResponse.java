package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class BaggageIdResponse {
private boolean successful;
private String message;
private String baggageId; // Unique baggage identifier
}
