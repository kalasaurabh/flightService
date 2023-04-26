package com.qatarairways.adapter.flight.service;

import com.qatarairways.adapter.flight.FlightAvailabilityService;
import com.qatarairways.adapter.flight.TestDataSetup;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import com.qatarairways.adapter.flight.service.impl.FlightSearchServiceImpl;
import com.qatarairways.adapter.flight.util.FlightUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@ExtendWith(MockitoExtension.class)
public class FlightSearchServiceImplTest extends TestDataSetup {

    @InjectMocks
    private FlightSearchServiceImpl flightSearchService;

    @Mock
    private FlightAvailabilityService availabilityService;

    @Mock
    private FlightUtils flightUtil;

    @Test
    public void test_sortTheListBasedOnSortCriteria(){
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2,"price");
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, false, 5L, 2, "price");
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, true, 5L, 2, "price");
        Collection<FlightSummaryDTO> flights = new ArrayList<>(Arrays.asList(flight1, flight2, flight3));
    }

}
