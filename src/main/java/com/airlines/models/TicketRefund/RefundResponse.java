package com.airlines.models.TicketRefund;

import lombok.Data;

@Data
public class RefundResponse {
private boolean isEligible;
private String message;
private String refundId;
private boolean refundProcessed;

// Getters and Setters
}

