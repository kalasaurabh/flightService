package com.qatarairways.adapter.flight;


import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class TestDataSetup {

    /**
     *
     * @param origin
     * @param destination
     * @param departureDate
     * @param numTravelers
     * @param cancel
     * @param maxPrice
     * @param sortCriteria
     * @param limit
     * @return
     */
    public static FlightSearchRequestDTO createFlightSearchRequest(String origin, String destination, String departureDate, int numTravelers, boolean cancel, Float maxPrice, String sortCriteria, Integer limit) {
        return FlightSearchRequestDTO.builder()
                .origin(origin)
                .destination(destination)
                .departureDate(LocalDate.parse(departureDate))
                .numTravelers(numTravelers)
                .cancellationPossible(cancel)
                .maxPrice(maxPrice)
                .sortCriteria(sortCriteria)
                .limit(limit)
                .build();
    }

    /**
     *
     * @param airline
     * @param deptDate
     * @param arrDate
     * @param price
     * @param cancelable
     * @return
     */
    @SneakyThrows
    public static FlightSummary createFlightSummary(String airline, String deptDate, String arrDate, float price, boolean cancelable ){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date departure = formatter.parse(deptDate);
        Date arrival = formatter.parse(arrDate);
        FlightSummary flightSummary = FlightSummary.builder()
                .airlineCode(airline)
                .departureTime(departure)
                .arrivalTime(arrival)
                .averagePriceInUsd(price)
                .cancellationPossible(cancelable)
                .build();

        return flightSummary;
    }

    /**
     *
     * @param airlineCode
     * @param departureAt
     * @param arrivalAt
     * @param averagePriceInUsd
     * @param isCancellable
     * @param duration
     * @param numSeatsAvailable
     * @return
     */
    public static FlightSummaryDTO createFlightDTO(String airlineCode, LocalDateTime departureAt,
                                                   LocalDateTime arrivalAt, Float averagePriceInUsd,
                                                       boolean isCancellable, Long duration,
                                                       int numSeatsAvailable) {
        return FlightSummaryDTO.builder()
                .airlineCode(airlineCode)
                .departureTime(departureAt)
                .arrivalTime(arrivalAt)
                .averagePriceInUsd(averagePriceInUsd)
                .cancellationPossible(isCancellable)
                .duration(duration)
                .numSeatsAvailable(numSeatsAvailable)
                .build();
    }

    /**
     *
     * @param airlineCode
     * @param departureAt
     * @param arrivalAt
     * @param averagePriceInUsd
     * @param isCancellable
     * @param duration
     * @param numSeatsAvailable
     * @param sortCriteria
     * @return
     */
    public static FlightSummaryDTO createFlightDTO(String airlineCode, LocalDateTime departureAt,
                                                   LocalDateTime arrivalAt, Float averagePriceInUsd,
                                                   boolean isCancellable, Long duration,
                                                   int numSeatsAvailable, String sortCriteria) {
        return FlightSummaryDTO.builder()
                .airlineCode(airlineCode)
                .departureTime(departureAt)
                .arrivalTime(arrivalAt)
                .averagePriceInUsd(averagePriceInUsd)
                .cancellationPossible(isCancellable)
                .duration((long) (arrivalAt.getSecond() - departureAt.getSecond()))
                .numSeatsAvailable(numSeatsAvailable)
                .sortCriteria(sortCriteria)
                .build();
    }
}
