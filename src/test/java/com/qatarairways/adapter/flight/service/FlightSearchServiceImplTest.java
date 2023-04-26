package com.qatarairways.adapter.flight.service;

import com.qatarairways.adapter.flight.FlightAvailabilityRequest;
import com.qatarairways.adapter.flight.FlightAvailabilityService;
import com.qatarairways.adapter.flight.FlightSummary;
import com.qatarairways.adapter.flight.TestDataSetup;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import com.qatarairways.adapter.flight.mapper.FlightRequestMapper;
import com.qatarairways.adapter.flight.service.impl.FlightSearchServiceImpl;
import com.qatarairways.adapter.flight.util.FlightUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightSearchServiceImplTest extends TestDataSetup {

    @InjectMocks
    private FlightSearchServiceImpl flightSearchService;

    @Mock
    private FlightAvailabilityService availabilityService;

    @Mock
    private FlightUtils flightUtil;

    @Test
    public void test_verifyTheCorrectResponseIsGenerated(){

        // given

        FlightSearchRequestDTO request = createFlightSearchRequest("NYC", "STL", "2022-03-01",
                2, true, 500.00f, "price", 5);

        FlightAvailabilityRequest availabilityRequest = FlightRequestMapper.toFlightAvailabilityRequest(request);
        FlightSummary flightSummary1 = createFlightSummary("Flight1", "2022-03-01", "2022-03-02", 200f, true);
        FlightSummary flightSummary2 = createFlightSummary("Flight2", "2022-03-01", "2022-03-02", 200f, true);
        FlightSummary flightSummary3 = createFlightSummary("Flight3", "2022-03-01", "2022-03-02", 200f, true);

        Collection<FlightSummary> availableFlights = Arrays.asList(flightSummary1,flightSummary2,flightSummary3);

        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2,"price");
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, false, 5L, 2, "price");
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, true, 5L, 2, "price");
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3);

        // when
        when(availabilityService.getAvailableFlights(availabilityRequest)).thenReturn(availableFlights);
        when(flightUtil.filterResponse(any(), any())).thenReturn(flights);
        when(flightUtil.sortFlights(flights,request.getSortCriteria())).thenReturn(flights);
        when(flightUtil.getLimitedFlightList(request.getLimit(),flights)).thenReturn(flights);

        // call the method
        Collection<String> airlines = flightSearchService.searchFlights(request);

        // then
        assertThat(airlines).hasSize(3);
        assertThat(airlines.equals(Arrays.asList("Flight1","Flight2","Flight3"))).isEqualTo(true);

    }

}
