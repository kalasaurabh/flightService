package com.qatarairways.adapter.flight.service.impl;

import com.qatarairways.adapter.flight.FlightAvailabilityRequest;
import com.qatarairways.adapter.flight.FlightAvailabilityService;
import com.qatarairways.adapter.flight.FlightSummary;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import com.qatarairways.adapter.flight.service.FlightSearchService;
import com.qatarairways.adapter.flight.util.FlightUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.qatarairways.adapter.flight.mapper.FlightRequestMapper.toFlightAvailabilityRequest;
import static com.qatarairways.adapter.flight.mapper.FlightSummaryMapper.toFlightDTO;

public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightAvailabilityService flightAvailabilityService;

    private final FlightUtils flightUtil;

    public FlightSearchServiceImpl(FlightAvailabilityService flightAvailabilityService, FlightUtils flightUtil) {
        this.flightAvailabilityService = flightAvailabilityService;
        this.flightUtil = flightUtil;
    }

    /**
     * This performs a search based on the request provided by the customer and also performs
     * a filter operation based on the filter criteria
     */
    @Override
    public Collection<FlightSummaryDTO> searchFlights(FlightSearchRequestDTO flightSearchRequestDTO) {

        // converts the request object to the object of external face
        FlightAvailabilityRequest request =  toFlightAvailabilityRequest(flightSearchRequestDTO);

        // calls the external service for loading the summary of the flights
        Collection<FlightSummary> flights = flightAvailabilityService.getAvailableFlights(request);

        Collection<FlightSummaryDTO> flightDTOS = flightUtil.filterResponse(toFlightDTO(flights,
                                                flightSearchRequestDTO.getSortCriteria()), flightSearchRequestDTO);

        // sorts the collection based on the sortcriteria
        flightUtil.sortFlights(flightDTOS, flightSearchRequestDTO.getSortCriteria());
        return flightUtil.getLimitedFlightList(flightSearchRequestDTO.getLimit(), flightDTOS);
    }


}
