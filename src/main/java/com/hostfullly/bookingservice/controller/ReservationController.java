package com.hostfullly.bookingservice.controller;

import com.hostfullly.bookingservice.dto.BlockDTO;
import com.hostfullly.bookingservice.dto.BookingDTO;
import com.hostfullly.bookingservice.exception.BookingCannotBeCancelledException;
import com.hostfullly.bookingservice.exception.InvalidStartTimeException;
import com.hostfullly.bookingservice.exception.OverlappingReservationException;
import com.hostfullly.bookingservice.exception.ReservationNotFoundException;
import com.hostfullly.bookingservice.request.CreateBlockRequest;
import com.hostfullly.bookingservice.request.UpdateBookingRequest;
import com.hostfullly.bookingservice.response.BlockResponse;
import com.hostfullly.bookingservice.response.BookingResponse;
import com.hostfullly.bookingservice.response.GetBlocksResponse;
import com.hostfullly.bookingservice.response.GetBookingsResponse;
import com.hostfullly.bookingservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<GetBookingsResponse> getAllReservations() {
        log.info("- IN - ReservationController | getAllBookings()");
        List<BookingDTO> bookings = reservationService.getAllReservations();
        GetBookingsResponse response = GetBookingsResponse.builder().bookingDTOList(bookings).build();
        log.info("{} - OUT - ReservationController | getAllBookings()", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/bookings/all")
    public ResponseEntity<GetBookingsResponse> getAllBookings() {
        log.info("- IN - ReservationController | getAllBookings()");
        List<BookingDTO> bookings = reservationService.getAllBookings();
        GetBookingsResponse response = GetBookingsResponse.builder().bookingDTOList(bookings).build();
        log.info("{} - OUT - ReservationController | getAllBookings()", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/bookings")
    public ResponseEntity<GetBookingsResponse> getCurrentBookings() {
        log.info("- IN - ReservationController | getCurrentBookings()");
        List<BookingDTO> bookings = reservationService.getCurrentBookings();
        GetBookingsResponse response = GetBookingsResponse.builder().bookingDTOList(bookings).build();
        log.info("{} - OUT - ReservationController | getCurrentBookings()", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/bookings/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Integer id) throws ReservationNotFoundException {
        log.info("{} - IN - ReservationController | getBooking()", id);
        BookingDTO booking = reservationService.getBookingById(id);
        BookingResponse response = BookingResponse.builder().bookingDTO(booking).build();
        log.info("{} - OUT - ReservationController | getBooking()", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/blocks")
    public ResponseEntity<GetBlocksResponse> getBlocks() {
        log.info("- IN - ReservationController | getBlocks()");
        List<BlockDTO> blocks = reservationService.getAllBlocks();
        GetBlocksResponse response = GetBlocksResponse.builder().blockDTOList(blocks).build();
        log.info("{} - OUT - ReservationController | getBlocks()", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/blocks/{id}")
    public ResponseEntity<BlockResponse> getBlock(@PathVariable Integer id) throws ReservationNotFoundException {
        log.info("{} - IN - ReservationController | getBlock()", id);
        BlockDTO blockDTO = reservationService.getBlockById(id);
        BlockResponse response = BlockResponse.builder().blockDTO(blockDTO).build();
        log.info("{} - OUT - ReservationController | getBlock()", response);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingDTO bookingDTO) throws InvalidStartTimeException, OverlappingReservationException {
        log.info("- IN - ReservationController | createBooking()");
        BookingDTO updatedBooking = reservationService.createBooking(bookingDTO);
        BookingResponse response = BookingResponse.builder().bookingDTO(updatedBooking).build();
        log.info("{} - OUT - ReservationController | createBooking()", response);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/blocks")
    public ResponseEntity<BlockResponse> createBlock(@RequestBody CreateBlockRequest request) throws OverlappingReservationException, InvalidStartTimeException {
        log.info("- IN - ReservationController | createBlock()");
        BlockDTO block = reservationService.createBlock(request);
        BlockResponse response = BlockResponse.builder().blockDTO(block).build();
        log.info("{} - OUT - ReservationController | createBlock()", response);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable("id") Integer id, @RequestBody UpdateBookingRequest request) throws ReservationNotFoundException, InvalidStartTimeException, OverlappingReservationException {
        log.info("{} - IN - ReservationController | updateBooking()", id);
        BookingDTO updatedBooking = reservationService.updateBooking(id, request);
        BookingResponse response = BookingResponse.builder().bookingDTO(updatedBooking).build();
        log.info("{} - OUT - ReservationController | updateBooking()", response);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable("id") Integer id) throws ReservationNotFoundException, BookingCannotBeCancelledException {
        log.info("{} - IN - ReservationController | cancelBooking()", id);
        BookingDTO updatedBooking = reservationService.cancelBooking(id);
        BookingResponse response = BookingResponse.builder().bookingDTO(updatedBooking).build();
        log.info("{} - OUT - ReservationController | cancelBooking()", response);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/blocks/{id}")
    public ResponseEntity<Object> removeBlock(@PathVariable Integer id) throws ReservationNotFoundException {
        log.info("{} - IN - ReservationController | removeBlock()", id);
        reservationService.removeBlock(id);
        log.info("- OUT - ReservationController | removeBlock()");
        return ResponseEntity.noContent().build();
    }
}
