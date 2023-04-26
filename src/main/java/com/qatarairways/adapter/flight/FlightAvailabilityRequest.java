package com.qatarairways.adapter.flight;

import java.time.LocalDate;

import lombok.NonNull;
import lombok.Value;

/**
 * Represents a flight availability request.
 */
@Value
public class FlightAvailabilityRequest {
    /**
     * The origin (departure) of the flights.
     */
    @NonNull
    private final String origin;
    /**
     * The target destination of the flights.
     */
    @NonNull
    private final String destination;
    /**
     * The day of departure.
     */
    @NonNull
    //Fixme
    //changed the date type from util date to LocalDate
    private final LocalDate departureDate;
    /**
     * The required number of seats.
     */
    private final int numberOfTravellers;
}
