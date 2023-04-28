package com.hostfullly.bookingservice.exception;

public class InvalidStartTimeException extends Exception {
    public InvalidStartTimeException(String start_time_cannot_be_before_end_time) {
        super(start_time_cannot_be_before_end_time);
    }
}
