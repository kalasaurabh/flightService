package com.qatarairways.adapter.flight.mapper;

import com.qatarairways.adapter.flight.FlightSummary;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Provides conversion of {@link Collection<FlightSummary>}.
 */
public class FlightSummaryMapper {

    /**
     * Converts {@link FlightSummary} to {@link FlightSummaryDTO}.
     *
     * @param flights an instance of {@link FlightSummary}
     * @return an instance of {@link FlightSummaryDTO}
     */
    public static Collection<FlightSummaryDTO> toFlightDTO(Collection<FlightSummary> flights) {
        return flights.stream()
                .map(FlightSummaryMapper::convertDTO)
                .collect(Collectors.toList());
    }

    private static FlightSummaryDTO convertDTO(FlightSummary flightSummary) {
        return FlightSummaryDTO.builder()
                .airlineCode(flightSummary.getAirlineCode())
                .departureTime(convertToLocalDateTime(flightSummary.getDepartureTime()))
                .arrivalTime(convertToLocalDateTime(flightSummary.getArrivalTime()))
                .averagePriceInUsd(flightSummary.getAveragePriceInUsd())
                .duration(flightSummary.getArrivalTime().getTime() - flightSummary.getDepartureTime().getTime())
                .cancellationPossible(flightSummary.isCancellationPossible())
                .build();
    }

    private static LocalDateTime convertToLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
