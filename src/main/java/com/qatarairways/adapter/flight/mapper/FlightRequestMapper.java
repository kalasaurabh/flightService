package com.qatarairways.adapter.flight.mapper;

import com.qatarairways.adapter.flight.FlightAvailabilityRequest;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
                convertLocalDateToDate(searchRequest.getDepartureDate()),
                searchRequest.getNumTravelers()
        );
    }

    private static Date convertLocalDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
