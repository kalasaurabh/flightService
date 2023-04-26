package com.qatarairways.adapter.flight.service.helper;

import com.qatarairways.adapter.flight.TestDataSetup;
import com.qatarairways.adapter.flight.dto.FlightSearchRequestDTO;
import com.qatarairways.adapter.flight.dto.FlightSummaryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FlightSearchServiceHelperTest extends TestDataSetup {

    @InjectMocks
    private FlightSearchServiceHelper flightSearchServiceHelper;

    @Test
    public void test_filterFlightResponseForCancelFlights(){
        // given
        // Create some sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3);

        // Create a search request that includes cancellable flights
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, true, 0.0f, null, null);

        // when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        // then
        assertThat(filteredFlights).hasSize(2);
        assertThat(filteredFlights.contains("Flight1")).isEqualTo(true);
        assertThat(filteredFlights.contains("Flight3")).isEqualTo(true);
    }

    @Test
    public void test_filterFlightResponseForMaxPrice(){
        // given
        // Create some sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2);

        // Create a search request that includes maximum average price
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, false, 150f, null, null);

        // when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        //then
        assertThat(filteredFlights).hasSize(1);
        assertThat(filteredFlights.contains("Flight1")).isEqualTo(true);
    }


    @Test
    public void test_filterFlightResponseForCancelFlightsANDMaxPrice(){
        // given
        // Create some sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3);

        // Create a search request that includes both cancellable flights and a maximum average price
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, true, 150f, null, null);

        //when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        //then
        assertThat(filteredFlights).hasSize(1);
        assertThat(filteredFlights.contains("Flight1")).isEqualTo(true);
    }


    @Test
    public void test_FilterFlightWhenNoCriteriaGiven(){
        // given
        // Create some sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3);

        // Create a search request that includes both cancellable flights and a maximum average price
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, false, 0.0f, null, null);

        //when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        //then
        assertThat(filteredFlights).hasSize(3);
    }


    @Test
    public void test_ResponseWhenRequestHasBlankLimit(){
        // given
        //create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // Create a search request that doesn't have the limit
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, false, 0.0f, null, null);

        // when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        // then
        assertThat(filteredFlights).hasSize(3);
        assertThat(filteredFlights.contains("Flight1")).isEqualTo(true);
        assertThat(filteredFlights.contains("Flight2")).isEqualTo(true);
        assertThat(filteredFlights.contains("Flight3")).isEqualTo(true);
    }

    @Test
    public void test_ResponseWhenRequestHasZeroLimit(){
        // given
        //create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // Create a search request that doesn't have the limit
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, false, 0.0f, null, 0);

        // when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        // then
        assertThat(filteredFlights).hasSize(3);
        assertThat(filteredFlights.contains("Flight1")).isEqualTo(true);
        assertThat(filteredFlights.contains("Flight2")).isEqualTo(true);
        assertThat(filteredFlights.contains("Flight3")).isEqualTo(true);
    }

    @Test
    public void test_ResponseWhenRequestHasLimit(){
        // given
        // create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // Create a search request has the limit
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, false, 0.0f, null, 2);

        // when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        //then
        assertThat(filteredFlights).hasSize(2);
        assertThat(filteredFlights.contains("Flight1")).isEqualTo(true);
        assertThat(filteredFlights.contains("Flight2")).isEqualTo(true);
    }

    @Test
    public void test_ResponseWhenRequestHasFilterOptionAndHasLimit(){
        // given
        // create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 5L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 5L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 5L, 2);
        FlightSummaryDTO flight5 = createFlightDTO("Flight5", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 600f, false, 5L, 2);
        FlightSummaryDTO flight6 = createFlightDTO("Flight6", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 700f, true, 5L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // Create a search request has the limit
        FlightSearchRequestDTO searchRequest = createFlightSearchRequest("NYJ", "STL", "2023-04-01", 2, true, 450f, null, 3);

        // when
        List filteredFlights = (List)flightSearchServiceHelper.filterAndLimitResponse(flights, searchRequest);

        //then
        assertThat(filteredFlights).hasSize(3);
    }

    @Test
    public void test_sortFlightsOnDuration(){
        // given
        // create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200f, false, 4L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, true, 2L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 3L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // when
        List<FlightSummaryDTO> sortFlights = (List)flightSearchServiceHelper.sortFlights(flights, "duration");

        //then
        assertThat(sortFlights.get(0).getAirlineCode()).isEqualTo("Flight3");
        assertThat(sortFlights.get(1).getAirlineCode()).isEqualTo("Flight4");
        assertThat(sortFlights.get(2).getAirlineCode()).isEqualTo("Flight2");
        assertThat(sortFlights.get(3).getAirlineCode()).isEqualTo("Flight1");
    }

    @Test
    public void test_sortFlightsOnPrice(){
        // given
        // create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, false, 4L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 2L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 500f, true, 3L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // when
        List<FlightSummaryDTO> sortFlights = (List)flightSearchServiceHelper.sortFlights(flights, "price");

        //then
        assertThat(sortFlights.get(0).getAirlineCode()).isEqualTo("Flight3");
        assertThat(sortFlights.get(1).getAirlineCode()).isEqualTo("Flight2");
        assertThat(sortFlights.get(2).getAirlineCode()).isEqualTo("Flight1");
        assertThat(sortFlights.get(3).getAirlineCode()).isEqualTo("Flight4");
    }

    @Test
    public void test_sortingWhenNoCriteria() {
        // given
        // create sample flights
        FlightSummaryDTO flight1 = createFlightDTO("Flight1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 400f, true, 5L, 2);
        FlightSummaryDTO flight2 = createFlightDTO("Flight2", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300f, false, 4L, 2);
        FlightSummaryDTO flight3 = createFlightDTO("Flight3", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 100f, true, 2L, 2);
        FlightSummaryDTO flight4 = createFlightDTO("Flight4", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 500f, true, 3L, 2);
        Collection<FlightSummaryDTO> flights = Arrays.asList(flight1, flight2, flight3,flight4);

        // when
        List<FlightSummaryDTO> sortFlights = (List)flightSearchServiceHelper.sortFlights(flights, null);

        //then
        assertThat(sortFlights.get(0).getAirlineCode()).isEqualTo("Flight1");
        assertThat(sortFlights.get(1).getAirlineCode()).isEqualTo("Flight2");
        assertThat(sortFlights.get(2).getAirlineCode()).isEqualTo("Flight3");
        assertThat(sortFlights.get(3).getAirlineCode()).isEqualTo("Flight4");
    }
}
