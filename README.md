Flight Booking System – Backend (Spring Boot)

This project is a Flight Search and Ticket Booking REST API, developed using Spring Boot.
Users can search flights, book tickets, view ticket details, cancel bookings, and check booking history.
Admin can add flight inventory.

Features:
Admin Features:
-Add flight inventory

User Features:
-Search flights
-Book flights
-Auto-generated seat numbers
-Auto-generated unique PNR
-Cancel ticket
-View booking history by email


API Endpoints:

POST:(Add Flight Inventory)
http://localhost:8080/api/v1.0/flight/airline/inventory/add

Request Body:
{
  "airline": {
    "airlineName": "AirIndia3"
  },
  "flightNumber": "6E111",
  "fromPlace": "Hyderabad",
  "toPlace": "Delhi",
  "departureDateTime": "2025-11-20T09:30:00",
  "arrivalDateTime": "2025-11-20T11:45:00",
  "price": 35000,
  "totalSeats": 180,
  "ticketPrice": 4500
}


GET(Display All Flights):
http://localhost:8080/api/v1.0/flight/search

Response:
Returns all flights.

POST(Search Flights):
http://localhost:8080/api/v1.0/flight/search

Request Body:
{
  "fromPlace": "Hyderabad",
  "toPlace": "Delhi",
  "date": "2025-11-20",
  "roundTrip": true
}


POST(Book Ticket):
http://localhost:8080/api/v1.0/flight/booking/3

{
  "userName": "Divya",
  "userEmail": "divya3@gmail.com",
  "journeyDate": "2025-02-12",
  "mealType": "Veg",
  "roundTrip": true,
  "passengers": [
    {
      "passengerName": "Hema",
      "gender": "Female",
      "age": 20
    },
    {
      "passengerName": "Divya",
      "gender": "Female",
      "age": 22
    }
  ]
}


GET(Get Ticket By PNR):
http://localhost:8080/api/v1.0/flight/ticket/{PNR}

DELETE(Cancel Ticket):
http://localhost:8080/api/v1.0/flight/booking/cancel/{PNR}

GET(Booking History by Email):
http://localhost:8080/api/v1.0/flight/booking/history/{email}

Validation:
The project uses:
-@NotBlank
-@NotNull
-@Email
-@Min/@Max
-@Valid
