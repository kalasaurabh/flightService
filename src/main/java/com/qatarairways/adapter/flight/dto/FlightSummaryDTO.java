package com.qatarairways.adapter.flight.dto;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
/**
 * Represents a flight Summary response.
 */
@Value
@Builder
public class FlightSummaryDTO {
    /**
     * The airline code.
     */
    private String airlineCode;
    /**
     * The departure datetime of the flight.
     */
    private LocalDateTime departureTime;
    /**
     * The expected arrival datetime of the flight.
     */
    private LocalDateTime arrivalTime;
    /**
     * The average price of the seats on this flight.
     */
    private Float averagePriceInUsd;
    /**
     * Whether cancellation is possible for this flight.
     */
    private boolean cancellationPossible;
    /**
     * Number of seats available
     */
    private int numSeatsAvailable;
    /**
     * This is the sortCriteria
     */
    private String sortCriteria;
    /**
     * This is the duration of the flight
     */
    private Long duration;

}

