package com.airlines.controller;

import com.airlines.models.CheckIn.*;
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
@RequestMapping("/v2/check-in")
public class CheckIn {

// Define API paths
@Value("${api.checkin.travelDetails}")
private String travelDetailsApi;

@Value("${api.checkin.seatChange}")
private String seatChangeApi;

@Value("${api.checkin.extraLuggage}")
private String extraLuggageApi;

@Value("${api.checkin.baggageId}")
private String baggageIdApi;

@Value("${api.checkin.mealOptions}")
private String mealOptionsApi;

@Value("${api.checkin.boardingPass}")
private String boardingPassApi;

@Value("${api.checkin.realTimeUpdater}")
private String realTimeUpdaterApi;

@Value("${api.checkin.deleteBaggageId}")
private String deleteBaggageApi;

@Value("${api.checkin.rejectedCheckins}")
private String rejectedCheckinsApi;

@Autowired
private RestTemplate restTemplate;

@PostMapping("/submit-travel-details")
public ResponseEntity<?> submitTravelDetails(
        @RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
        @RequestBody TravelDetailsRequest travelDetailsRequest) throws URISyntaxException {

    travelDetailsRequest.setRequestId(UUID.randomUUID().toString());
    TravelDetailsResponse response = new TravelDetailsResponse();
    response.setSuccessful(true);
    response.setMessage("Travel details submitted successfully.");

    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI seatChangeUri = new URI(seatChangeApi);
        HttpEntity<TravelDetailsResponse> requestEntity = new HttpEntity<>(response, headers);
        SeatChangeResponse seatChangeResponse = restTemplate.exchange(seatChangeUri, HttpMethod.POST, requestEntity, SeatChangeResponse.class).getBody();

        return new ResponseEntity<>(seatChangeResponse, HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping("/seat-change")
public ResponseEntity<?> seatChange(
        @RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
        @RequestBody SeatChangeRequest seatChangeRequest) throws URISyntaxException {

    seatChangeRequest.setRequestId(UUID.randomUUID().toString());
    SeatChangeResponse response = new SeatChangeResponse();
    response.setSuccessful(true);
    response.setMessage("Seat change requested successfully.");

    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI extraLuggageUri = new URI(extraLuggageApi);
        HttpEntity<SeatChangeResponse> requestEntity = new HttpEntity<>(response, headers);
        ExtraLuggageResponse extraLuggageResponse = restTemplate.exchange(extraLuggageUri, HttpMethod.POST, requestEntity, ExtraLuggageResponse.class).getBody();

        return new ResponseEntity<>(extraLuggageResponse, HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping("/extra-luggage")
public ResponseEntity<?> extraLuggage(
        @RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
        @RequestBody ExtraLuggageRequest extraLuggageRequest) throws URISyntaxException {

    extraLuggageRequest.setRequestId(UUID.randomUUID().toString());
    ExtraLuggageResponse response = new ExtraLuggageResponse();
    response.setSuccessful(true);
    response.setMessage("Extra luggage information submitted successfully.");

    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI baggageIdUri = new URI(baggageIdApi);
        HttpEntity<ExtraLuggageResponse> requestEntity = new HttpEntity<>(response, headers);
        BaggageIdResponse baggageIdResponse = restTemplate.exchange(baggageIdUri, HttpMethod.POST, requestEntity, BaggageIdResponse.class).getBody();

        return new ResponseEntity<>(baggageIdResponse, HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping("/baggage-id")
public ResponseEntity<?> baggageId(
        @RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
        @RequestBody BaggageIdRequest baggageIdRequest) throws URISyntaxException {

    baggageIdRequest.setRequestId(UUID.randomUUID().toString());
    BaggageIdResponse response = new BaggageIdResponse();
    response.setSuccessful(true);
    response.setMessage("Baggage ID generated successfully.");

    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        URI mealOptionsUri = new URI(mealOptionsApi);
        HttpEntity<BaggageIdResponse> requestEntity = new HttpEntity<>(response, headers);
        MealOptionsResponse mealOptionsResponse = restTemplate.exchange(mealOptionsUri, HttpMethod.POST, requestEntity, MealOptionsResponse.class).getBody();
        return new ResponseEntity<>(mealOptionsResponse, HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping("/meal-options")
public ResponseEntity<?> mealOptions(
        @RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
        @RequestBody MealOptionsRequest mealOptionsRequest) throws URISyntaxException {

    mealOptionsRequest.setRequestId(UUID.randomUUID().toString());
    MealOptionsResponse response = new MealOptionsResponse();
    response.setSuccessful(true);
    response.setRequestId(mealOptionsRequest.getRequestId());
    response.setMealId(String.valueOf(UUID.randomUUID()));
    response.setMessage("Meal options selected successfully.");
    if (enableTracing) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("enableTracing", String.valueOf(enableTracing));
        headers.add("deviateResponse", String.valueOf(deviateResponse));

        StitchRequestBody stitchRequestBody = new StitchRequestBody();
        stitchRequestBody.setMealOption(response.getRequestId());
        stitchRequestBody.setRequestId(response.getRequestId());
        stitchRequestBody.setSeatNumber(null);
        URI boardingPassUri = new URI(boardingPassApi);
        HttpEntity<?> requestEntity = new HttpEntity<>(stitchRequestBody, headers);
        BoardingPassResponse boardingPassResponse = restTemplate.exchange(boardingPassUri, HttpMethod.POST, requestEntity, BoardingPassResponse.class).getBody();

        return new ResponseEntity<>(boardingPassResponse, HttpStatus.OK);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping("/boarding-pass")
public ResponseEntity<?> boardingPass(
        @RequestHeader(value = "enableTracing", required = false) boolean enableTracing,
        @RequestHeader(value = "deviateResponse", required = false) boolean deviateResponse,
        @RequestBody StitchRequestBody stitchRequestBody) throws URISyntaxException {

    BoardingPassResponse response = new BoardingPassResponse();
    response.setSuccessful(true);
    response.setBoardingId(String.valueOf(UUID.randomUUID()));
    response.setMessage("Boarding pass generated successfully.");
    stitchRequestBody.setBoardingId(String.valueOf(UUID.randomUUID()));
    if (deviateResponse) {
        response.setSuccessful(false);
        response.setMessage("Boarding pass Generation Failed Due to invalid data. Please Contact Customer");
        if (enableTracing) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("enableTracing", String.valueOf(enableTracing));
            headers.add("deviateResponse", String.valueOf(deviateResponse));

            HttpEntity<?> deleteRequestEntity = new HttpEntity<>( headers);
            String uri = String.format(deleteBaggageApi,stitchRequestBody.getBaggageId());
             restTemplate.exchange(new URI(uri), HttpMethod.DELETE, deleteRequestEntity, DeleteBaggageResponse.class).getBody();

            URI rejectedCheckinsUri = new URI(rejectedCheckinsApi);
            HttpEntity<RejectedCheckinsRequest> rejectedRequestEntity = new HttpEntity<>(new RejectedCheckinsRequest(), headers);
            restTemplate.exchange(rejectedCheckinsUri, HttpMethod.PUT, rejectedRequestEntity, RejectedCheckinsResponse.class).getBody();

            return new ResponseEntity<>(stitchRequestBody, HttpStatus.OK);
        }
    } else {
        if (enableTracing) {
            URI realTimeUpdaterUri = new URI(realTimeUpdaterApi);
            HttpHeaders headers = new HttpHeaders();
            headers.add("enableTracing", String.valueOf(enableTracing));
            headers.add("deviateResponse", String.valueOf(deviateResponse));
            HttpEntity<?> requestEntity = new HttpEntity<>(stitchRequestBody, headers);
            restTemplate.exchange(realTimeUpdaterUri, HttpMethod.POST, requestEntity, Object.class);
            return new ResponseEntity<>(stitchRequestBody, HttpStatus.OK);
        }
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@DeleteMapping("/delete-baggage/{baggageId}")
public ResponseEntity<?> deleteBaggage(@PathVariable(value = "baggageId") String baggageId) {
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

@PutMapping("/rejected-checkins")
public ResponseEntity<RejectedCheckinsResponse> rejectedCheckins(
        @RequestBody RejectedCheckinsRequest rejectedCheckinsRequest) {

    RejectedCheckinsResponse response = new RejectedCheckinsResponse();
    response.setSuccessful(true);
    response.setMessage("Rejected check-in recorded successfully.");

    // Logic for recording rejected check-in would go here

    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping("/real-time-updater")
public ResponseEntity<?> updateRealTimeDetails(
        @RequestBody StitchRequestBody stitchRequestBody) {
    // Logic to update real-time details
    // This is just a placeholder for your logic.
    RealTimeUpdaterResponse response = new RealTimeUpdaterResponse();
    response.setSuccessful(true);
    response.setMessage("Real-time details updated successfully.");

    return new ResponseEntity<>(response, HttpStatus.OK);
}
}