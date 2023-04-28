package com.hostfullly.bookingservice.exception;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException(String no_data_found) {
        super(no_data_found);
    }
}
