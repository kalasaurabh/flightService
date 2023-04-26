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
     *
     * @param flightSummaryDTOS      the collection of flight summary
     * @param flightSearchRequestDTO the flight search request object
     * @return the collection of filtered FlightSummaryDTO
     */
    public Collection<FlightSummaryDTO> filterResponse(Collection<FlightSummaryDTO> flightSummaryDTOS,
                                                       FlightSearchRequestDTO flightSearchRequestDTO) {
        if (flightSearchRequestDTO.isCancellationPossible()) {
            flightSummaryDTOS = flightSummaryDTOS.stream().filter(FlightSummaryDTO::isCancellationPossible)
                    .collect(Collectors.toList());
        }
        Optional<Float> maxPrice = Optional.ofNullable(flightSearchRequestDTO.getMaxPrice());
        if (maxPrice.isPresent() && maxPrice.get() != 0) {
            flightSummaryDTOS = flightSummaryDTOS.stream().filter(flight -> flight.getAveragePriceInUsd()
                    .compareTo(maxPrice.get()) < 0).collect(Collectors.toList());
        }
        return flightSummaryDTOS;
    }

    /**
     * Limits the response based on the input provided
     *
     * @param limit
     * @param flights
     * @return
     */
    public Collection<FlightSummaryDTO> limitFlightList(Integer limit, Collection<FlightSummaryDTO> flights) {
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        return flights.stream().limit(limit).collect(Collectors.toList());
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
            flights = flights.stream().sorted(comparator).collect(Collectors.toList());
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
}
