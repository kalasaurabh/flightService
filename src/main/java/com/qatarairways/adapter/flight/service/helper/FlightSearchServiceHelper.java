package com.qatarairways.adapter.flight.service.helper;

import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightSearchServiceHelper {


    private static final int DEFAULT_LIMIT = 3;

    private static final String DURATION = "duration";

    private static final String PRICE = "price";

    /**
     * Filters the response based on cancellation and max price.
     * Sets the limit of response to default if not provided
     * @param flightSummaryDTOS      the collection of flight summary
     * @param flightSearchRequestDTO the flight search request object
     * @return the collection of filtered FlightSummaryDTO
     */
    public Collection<String> filterAndLimitResponse(Collection<FlightSummaryDTO> flightSummaryDTOS,
                                                       FlightSearchRequestDTO flightSearchRequestDTO) {
        int limit = getLimit(flightSearchRequestDTO);
        Collection<String> filteredFlights = null;
        if (flightSearchRequestDTO.isCancellationPossible()) {
            filteredFlights = flightSummaryDTOS.stream().filter(FlightSummaryDTO::isCancellationPossible)
                    .map(FlightSummaryDTO::getAirlineCode).limit(limit)
                    .collect(Collectors.toList());
        }
        Optional<Float> maxPrice = Optional.ofNullable(flightSearchRequestDTO.getMaxPrice());
        if (maxPrice.isPresent() && maxPrice.get() != 0) {
            filteredFlights = flightSummaryDTOS.stream().filter(flight -> flight.getAveragePriceInUsd()
                    .compareTo(maxPrice.get()) < 0).map(FlightSummaryDTO::getAirlineCode)
                    .limit(limit).collect(Collectors.toList());
        }
        if (filteredFlights == null) {
            filteredFlights = flightSummaryDTOS.stream().map(FlightSummaryDTO::getAirlineCode).limit(limit)
                    .collect(Collectors.toList());
        }
        return filteredFlights;
    }

    /**
     * Sorts the response based on the input provided
     *
     * @param flights
     * @param sortCriteria
     * @return
     */
    public Collection<FlightSummaryDTO> sortFlights(Collection<FlightSummaryDTO> flights, String sortCriteria) {
        Comparator<FlightSummaryDTO> comparator = getComparator(sortCriteria);
        if (ObjectUtils.isNotEmpty(comparator)) {
            flights = (Collection) flights.stream().sorted(comparator).collect(Collectors.toList());
        }
        return flights;
    }

    private Comparator<FlightSummaryDTO> getComparator(String sortCriteria) {
        Comparator<FlightSummaryDTO> comparator = null;
        if (DURATION.equalsIgnoreCase(sortCriteria)) {
            comparator = Comparator.comparingLong(FlightSummaryDTO::getDuration);
        } else if (PRICE.equalsIgnoreCase(sortCriteria)) {
            comparator = Comparator.comparingDouble(FlightSummaryDTO::getAveragePriceInUsd);
        }
        return comparator;
    }

    private Integer getLimit(FlightSearchRequestDTO flightSearchRequestDTO) {
        Integer limit = flightSearchRequestDTO.getLimit();
        return limit != null && limit != 0 ? limit : DEFAULT_LIMIT;
    }

}
