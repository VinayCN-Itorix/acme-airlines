package com.airlines.models.TicketRefund;

import lombok.Data;

@Data
public class RefundRequest {
private String ticketId;
private String customerId;
private String email;
private String phoneNumber;
private String requestId;
}
