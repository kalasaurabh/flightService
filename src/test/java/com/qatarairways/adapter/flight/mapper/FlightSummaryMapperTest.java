package com.qatarairways.adapter.flight.mapper;

import com.qatarairways.adapter.flight.FlightSummary;
import com.qatarairways.adapter.flight.TestDataSetup;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightSummaryMapperTest extends TestDataSetup {


    @Test
    public void testFlightSummary_ReturnsRightObject() {
        // given

        FlightSummary flightSummary1 = createFlightSummary("KF", "2020-01-01", "2020-01-02", 200f, true);

        FlightSummary flightSummary2 = createFlightSummary("SJ", "2021-01-01", "2021-01-02", 150f, false);

        List<FlightSummary> flights = Arrays.asList(flightSummary1, flightSummary2);

        // when
        List<FlightSummaryDTO> result = (List)FlightSummaryMapper.toFlightDTO(flights, "duration");
        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAirlineCode()).isEqualTo("KF");
        assertThat(result.get(0).getAveragePriceInUsd()).isEqualTo(200f);
        assertThat(result.get(0).isCancellationPossible()).isEqualTo(true);
        assertThat(result.get(0).getSortCriteria()).isEqualTo("duration");

        assertThat(result.get(1).getAirlineCode()).isEqualTo("SJ");
        assertThat(result.get(1).getAveragePriceInUsd()).isEqualTo(150f);
        assertThat(result.get(1).isCancellationPossible()).isEqualTo(false);
        assertThat(result.get(1).getSortCriteria()).isEqualTo("duration");
    }
}
