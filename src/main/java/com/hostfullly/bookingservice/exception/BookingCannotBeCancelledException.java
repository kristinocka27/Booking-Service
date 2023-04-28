package com.hostfullly.bookingservice.exception;

public class BookingCannotBeCancelledException extends Exception {
    public BookingCannotBeCancelledException(String booking_cannot_be_cancelled) {
        super(booking_cannot_be_cancelled);
    }
}

