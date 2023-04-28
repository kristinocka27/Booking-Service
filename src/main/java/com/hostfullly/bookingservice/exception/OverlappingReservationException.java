package com.hostfullly.bookingservice.exception;

public class OverlappingReservationException extends Exception {
    public OverlappingReservationException(String reservation_cannot_be_created) {
        super(reservation_cannot_be_created);
    }
}
