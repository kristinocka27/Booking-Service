package com.hostfullly.bookingservice.controller;

import com.hostfullly.bookingservice.exception.BookingCannotBeCancelledException;
import com.hostfullly.bookingservice.exception.InvalidStartTimeException;
import com.hostfullly.bookingservice.exception.OverlappingReservationException;
import com.hostfullly.bookingservice.exception.ReservationNotFoundException;
import com.hostfullly.bookingservice.response.BaseResponse;
import com.hostfullly.bookingservice.response.ErrorDetails;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler({
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<BaseResponse> handleRuntimeExceptions(RuntimeException e) {
        BaseResponse baseResponse = createErrorResponse(e, HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(InvalidStartTimeException.class)
    public ResponseEntity<BaseResponse> handleRuntimeExceptions(InvalidStartTimeException e) {
        BaseResponse baseResponse = createErrorResponse(e, HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(BookingCannotBeCancelledException.class)
    public ResponseEntity<BaseResponse> handleRuntimeExceptions(BookingCannotBeCancelledException e) {
        BaseResponse baseResponse = createErrorResponse(e, HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(OverlappingReservationException.class)
    public ResponseEntity<BaseResponse> handleRuntimeExceptions(OverlappingReservationException e) {
        BaseResponse baseResponse = createErrorResponse(e, HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<BaseResponse> handleUserNotFoundException(ReservationNotFoundException e) {
        BaseResponse baseResponse = createErrorResponse(e, HttpStatus.NOT_FOUND.name());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
    }

    private BaseResponse createErrorResponse(Exception e, String errorName) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorDetails(ErrorDetails.builder().description(e.getMessage()).name(errorName).build());
        return baseResponse;
    }
}
