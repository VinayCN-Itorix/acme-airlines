package com.airlines.controller;

import com.airlines.models.TicketRefund.ProcessResponse;
import com.airlines.models.TicketRefund.RefundRequest;
import com.airlines.models.TicketRefund.RefundResponse;
import io.apiwiz.compliance.config.EnableCompliance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@EnableCompliance
@RequestMapping("airlines/v2/refund")
public class TicketRefund {

@Value("${api.refund.validate}")
private String validateRefund;

@Value("${api.refund.process}")
private String processRefund;

@Value("${api.refund.confirm}")
private String confirmRefund;

@Value("${api.refund.cancel}")
private String cancelRefund;

@Value("${api.refund.delete}")
private String deleteRefund;

@Value("${api.refund.updateSystem}")
private String updateSystem;

@Autowired
private RestTemplate restTemplate;

@PostMapping("/request")
public ResponseEntity<?> receiveRefundRequest(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                              @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                              @RequestBody RefundRequest refundRequest) throws URISyntaxException {
    refundRequest.setRequestId(String.valueOf(UUID.randomUUID()));
    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI validateUri = new URI(validateRefund);
        HttpEntity<RefundRequest> requestEntity = new HttpEntity<>(refundRequest, headers);
        RefundResponse validateResponse = restTemplate.exchange(validateUri, HttpMethod.POST, requestEntity, RefundResponse.class).getBody();

        return new ResponseEntity<>(validateResponse, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.OK);
}

// Step 2: Validate Refund
@PostMapping("/validate")
public ResponseEntity<?> validateRefund(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                        @RequestBody RefundRequest refundRequest) throws URISyntaxException {
    RefundResponse refundResponse = new RefundResponse();

    // Logic for valid refund scenario
    if (!deviateResponse) {
        refundResponse.setEligible(true);
        refundResponse.setRefundId(String.valueOf(UUID.randomUUID()));
        refundResponse.setRefundProcessed(true);
        refundResponse.setMessage("Refund has been processed");// Assume it's processed in the valid scenario

        if (enableTracing) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("enableTracing", String.valueOf(enableTracing));
            headers.add("deviateResponse", String.valueOf(deviateResponse));

            // Proceed to the next API call
            URI processUri = new URI(processRefund);
            HttpEntity<RefundRequest> requestEntity = new HttpEntity<>(refundRequest, headers);
            RefundResponse processResponse = restTemplate.exchange(processUri, HttpMethod.POST, requestEntity, RefundResponse.class).getBody();

//            HttpHeaders headers1 = new HttpHeaders();
//            headers.add("enableTracing", String.valueOf(enableTracing));
//            headers.add("deviateResponse", String.valueOf(deviateResponse));
//
//            URI updateUri = new URI(updateSystem);
//            HttpEntity<RefundResponse> requestEntitye = new HttpEntity<>(refundResponse, headers);
//            RefundResponse updateResponse = restTemplate.exchange(updateUri, HttpMethod.PUT, requestEntity, RefundResponse.class).getBody();

            return new ResponseEntity<>(refundResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(refundResponse, HttpStatus.OK);
    }

    // Logic for invalid refund scenario
    refundResponse.setEligible(false);
    refundResponse.setMessage("Refund is Rejected"); // Unique message per request
    refundResponse.setRefundId(String.valueOf(UUID.randomUUID())); // Unique refund ID
    refundResponse.setRefundProcessed(false); // Not processed for invalid

    // If enableTracing is true, proceed to cancel the refund
    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI cancelUri = new URI(cancelRefund);
        HttpEntity<RefundResponse> requestEntity = new HttpEntity<>(refundResponse, headers);
        RefundResponse cancelResponse = restTemplate.exchange(cancelUri, HttpMethod.POST, requestEntity, RefundResponse.class).getBody();

        // Return cancelResponse or the refundResponse with error information
        return new ResponseEntity<>(refundResponse, HttpStatus.OK);
    }

    // Return the populated response when tracing is not enabled
    return new ResponseEntity<>(refundResponse, HttpStatus.OK);
}

// Step 3: Process Refund
@PostMapping("/process")
public ResponseEntity<?> processRefund(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                       @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                       @RequestBody RefundResponse refundResponse) throws URISyntaxException {
    ProcessResponse processResponse = new ProcessResponse();
    processResponse.setRefundId(refundResponse.getRefundId());
    processResponse.setStatus(true);
    processResponse.setPaymentId(String.valueOf(UUID.randomUUID()));
    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI confirmUri = new URI(confirmRefund);
        HttpEntity<RefundResponse> requestEntity = new HttpEntity<>(refundResponse, headers);
        RefundResponse confirmResponse = restTemplate.exchange(confirmUri, HttpMethod.POST, requestEntity, RefundResponse.class).getBody();

        return new ResponseEntity<>(confirmResponse, HttpStatus.OK);
    }

    return new ResponseEntity<>(processResponse, HttpStatus.OK);
}

// Step 4: Confirm Refund
@PostMapping("/confirm")
public ResponseEntity<?> confirmRefund(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                       @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                       @RequestBody RefundResponse refundResponse) throws URISyntaxException {
    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI updateUri = new URI(updateSystem);
        HttpEntity<RefundResponse> requestEntity = new HttpEntity<>(refundResponse, headers);
        RefundResponse updateResponse = restTemplate.exchange(updateUri, HttpMethod.PUT, requestEntity, RefundResponse.class).getBody();

        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }

    return new ResponseEntity<>(refundResponse, HttpStatus.OK);
}

// Step 5: Cancel Refund (called when deviateResponse is true)
@PostMapping("/cancel")
public ResponseEntity<?> cancelRefund(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                      @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                      @RequestBody RefundResponse refundResponse) throws URISyntaxException {
    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));
        deleteRefund = String.format(deleteRefund,refundResponse.getRefundId());
        URI deleteUri = new URI(deleteRefund);
        HttpEntity<RefundResponse> requestEntity = new HttpEntity<>(refundResponse, headers);
        RefundResponse deleteResponse = restTemplate.exchange(deleteUri, HttpMethod.DELETE, requestEntity, RefundResponse.class).getBody();

        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    return new ResponseEntity<>(refundResponse, HttpStatus.OK);
}

@DeleteMapping("/delete/{refundId}")
public ResponseEntity<?> deleteRefund(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                      @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                      @PathVariable(value = "refundId") String refundId) throws URISyntaxException {

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

// Step 7: Update System Records
@PutMapping("/updateSystem")
public ResponseEntity<?> updateSystem(@RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
                                      @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
                                      @RequestBody RefundResponse refundResponse) {

    return new ResponseEntity<>(refundResponse, HttpStatus.OK);
}
}