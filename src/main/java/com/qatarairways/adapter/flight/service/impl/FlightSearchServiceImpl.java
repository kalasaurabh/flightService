package com.qatarairways.adapter.flight.service.impl;

import com.qatarairways.adapter.flight.FlightAvailabilityRequest;
import com.qatarairways.adapter.flight.FlightAvailabilityService;
import com.qatarairways.adapter.flight.FlightSummary;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import com.qatarairways.adapter.flight.service.FlightSearchService;
import com.qatarairways.adapter.flight.service.helper.FlightSearchServiceHelper;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.qatarairways.adapter.flight.mapper.FlightRequestMapper.toFlightAvailabilityRequest;
import static com.qatarairways.adapter.flight.mapper.FlightSummaryMapper.toFlightDTO;

public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightAvailabilityService flightAvailabilityService;

    private final FlightSearchServiceHelper flightSearchServiceHelper;

    public FlightSearchServiceImpl(FlightAvailabilityService flightAvailabilityService,
                                   FlightSearchServiceHelper flightSearchServiceHelper) {
        this.flightAvailabilityService = flightAvailabilityService;
        this.flightSearchServiceHelper = flightSearchServiceHelper;
    }

    /**
     * This performs a search based on the request provided by the customer and also performs
     * a filter operation based on the filter criteria
     */
    @Override
    public Collection<String> searchFlights(FlightSearchRequestDTO flightSearchRequestDTO) {

        // converts the request object to the object of external face
        FlightAvailabilityRequest request = toFlightAvailabilityRequest(flightSearchRequestDTO);

        // calls the external service for loading the summary of the flights
        Collection<FlightSummary> availableFlights = flightAvailabilityService.getAvailableFlights(request);

        // converts the response object here

        Collection<FlightSummaryDTO> flightSummaryDTOS = toFlightDTO(availableFlights);

        // sorts the collection based on the sortcriteria
        flightSummaryDTOS = flightSearchServiceHelper.sortFlights(flightSummaryDTOS, flightSearchRequestDTO.getSortCriteria());

        return flightSearchServiceHelper.filterAndLimitResponse(flightSummaryDTOS,flightSearchRequestDTO);

    }


}
