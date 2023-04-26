package com.qatarairways.adapter.flight.service;

import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;

import java.util.Collection;

public interface FlightSearchService {

    Collection<String> searchFlights(FlightSearchRequestDTO flightSearchRequestDTO);
}
