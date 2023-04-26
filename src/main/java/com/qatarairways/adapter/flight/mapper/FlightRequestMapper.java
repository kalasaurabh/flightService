package com.qatarairways.adapter.flight.mapper;

import com.qatarairways.adapter.flight.FlightAvailabilityRequest;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;

/**
 * Provides conversion of {@link FlightAvailabilityRequest}.
 */
public class FlightRequestMapper {

    /**
     * Converts {@link FlightSearchRequestDTO} to {@link FlightAvailabilityRequest}.
     *
     * @param searchRequest an instance of {@link FlightSearchRequestDTO}
     * @return an instance of {@link FlightAvailabilityRequest}
     */
    public static FlightAvailabilityRequest toFlightAvailabilityRequest(FlightSearchRequestDTO searchRequest) {
        return new FlightAvailabilityRequest(
                searchRequest.getOrigin(),
                searchRequest.getDestination(),
                searchRequest.getDepartureDate(),
                searchRequest.getNumTravelers()
        );
    }
}
