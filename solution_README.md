# Structure

The starting point of the application is FlightSearchService

The package structure is as follows:

dto: This will have the request in the form of search query and response

mapper: This will have the mapping mechanism 

service: 
    impl: This will have the implementation of the main entry point FlightSearchService
    helper: This will have the filtering and sorting logic implementation

# Implementation

FlightSearchService has searchFlights method, which is making call to the helper classes for 
performing filtering and sorting operations. 

Sorting is using factory pattern to choose the comparator. Any new comparator in future can be 
just added by adding another condition

#  Assumption

1) The sequence of filtering is cancelPossible flights first followed by max price.
2) LocalDate is used for exact date (like "2022-01-01") and LocalDateTime is used for time.
