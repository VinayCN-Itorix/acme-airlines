package com.airlines.models.TicketRefund;

import lombok.Data;

@Data
public class ProcessResponse {
private String refundId;
private boolean status;
private String paymentId;
}
