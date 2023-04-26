package com.qatarairways.adapter.flight.mapper;

import com.qatarairways.adapter.flight.FlightAvailabilityRequest;
import com.qatarairways.adapter.flight.TestDataSetup;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightRequestMapperTest extends TestDataSetup {

    @Test
    public void testFlightAvailability_ReturnsGivenObject() {
        // given
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYC", "STL", "2020-03-20", 20, true, 1f, "price", 3);

        // when
        FlightAvailabilityRequest result = FlightRequestMapper.toFlightAvailabilityRequest(searchRequest);

        // then
        assertThat(result.getOrigin()).isEqualTo("NYC");
        assertThat(result.getDestination()).isEqualTo("STL");
        assertThat(result.getDepartureDate()).isEqualTo("2020-03-20");
        assertThat(result.getNumberOfTravellers()).isEqualTo(20);
    }

}
