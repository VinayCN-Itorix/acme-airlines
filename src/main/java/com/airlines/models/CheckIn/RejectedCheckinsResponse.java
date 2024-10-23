package com.airlines.models.CheckIn;

import lombok.Data;

@Data
public class RejectedCheckinsResponse {
private boolean successful;
private String message;
}
