package com.qatarairways.adapter.flight.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

/**
 * Represents a flight search request.
 */
@Value
@Builder
public class FlightSearchRequestDTO {
    /**
     * The origin (departure) of the flights.
     */
    private String origin;
    /**
     * The target destination of the flights.
     */
    private String destination;
    /**
     * The day of departure.
     */
    private LocalDate departureDate;
    /**
     * The required number of seats.
     */
    private int numTravelers;
    /**
     * Whether cancellation is possible for this flight.
     */
    private boolean cancellationPossible;
    /**
     * The maximum price of the flight provided by the customer.
     */
    private float maxPrice;
    /**
     * The search criteria based on customer's preference.
     */
    private String sortCriteria;
    /**
     * The limit by which customer wants to display the response.
     */
    private Integer limit;
}
