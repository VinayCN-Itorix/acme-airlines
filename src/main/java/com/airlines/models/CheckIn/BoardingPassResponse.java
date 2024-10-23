package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class BoardingPassResponse {
private boolean successful;
private String message;
private String boardingId;
}