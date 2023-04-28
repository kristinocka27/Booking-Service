package com.hostfullly.bookingservice.service;

import com.hostfullly.bookingservice.dto.BlockDTO;
import com.hostfullly.bookingservice.dto.BookingDTO;
import com.hostfullly.bookingservice.dto.mapper.BlockMapper;
import com.hostfullly.bookingservice.dto.mapper.BookingMapper;
import com.hostfullly.bookingservice.entity.Reservation;
import com.hostfullly.bookingservice.enums.BookingStatusEnum;
import com.hostfullly.bookingservice.exception.BookingCannotBeCancelledException;
import com.hostfullly.bookingservice.exception.InvalidStartTimeException;
import com.hostfullly.bookingservice.exception.OverlappingReservationException;
import com.hostfullly.bookingservice.exception.ReservationNotFoundException;
import com.hostfullly.bookingservice.repository.ReservationRepository;
import com.hostfullly.bookingservice.request.CreateBlockRequest;
import com.hostfullly.bookingservice.request.UpdateBookingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final BookingMapper bookingMapper;
    private final BlockMapper blockMapper;
    private final ReservationRepository reservationRepository;

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) throws OverlappingReservationException, InvalidStartTimeException {
        log.info("- IN - ReservationService | createBooking()");
        if (isOverlappingOnCreate(bookingDTO.getStartTime(), bookingDTO.getEndTime(), bookingDTO.getPropertyId(), bookingDTO.getUserId())) {
            throw new OverlappingReservationException("Booking can not be created because of overlapping dates.");
        }
        bookingDTO.validateDates();
        Reservation booking = reservationRepository.save(bookingMapper.mapBookingDTOToEntity(bookingDTO));
        log.info("{} - OUT - ReservationService | createBooking()", booking);
        return bookingMapper.mapEntityToBookingDTO(booking);
    }

    public BookingDTO getBookingById(Integer id) throws ReservationNotFoundException {
        return bookingMapper.mapEntityToBookingDTO(reservationRepository.findBookingByIdAndStatusIn(id, Arrays.asList(BookingStatusEnum.CREATED, BookingStatusEnum.REBOOKED)).orElseThrow(() -> new ReservationNotFoundException("No active booking found with id: " + id)));
    }

    public List<BookingDTO> getCurrentBookings() {
        return reservationRepository.findAllByStatusNotIn(Arrays.asList(BookingStatusEnum.CANCELED, BookingStatusEnum.BLOCKED)).stream()
                .map(bookingMapper::mapEntityToBookingDTO).toList();
    }

    public List<BookingDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(bookingMapper::mapEntityToBookingDTO).toList();
    }

    public List<BookingDTO> getAllBookings() {
        return reservationRepository.findAllByStatusNot(BookingStatusEnum.BLOCKED).stream()
                .map(bookingMapper::mapEntityToBookingDTO).toList();
    }

    @Transactional
    public BookingDTO updateBooking(Integer id, UpdateBookingRequest request) throws ReservationNotFoundException, OverlappingReservationException, InvalidStartTimeException {
        log.info("{} - IN - ReservationService | updateBooking()", id);
        BookingDTO booking = getBookingById(id);
        booking.setStatus(BookingStatusEnum.REBOOKED.toString());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        if (isOverlappingOnUpdate(id, request.getStartTime(), request.getEndTime(), booking.getPropertyId(), booking.getUserId())) {
            throw new OverlappingReservationException("Booking can not be updated because of overlapping dates.");
        }
        booking.validateDates();
        reservationRepository.save(bookingMapper.mapBookingDTOToEntity(booking));
        log.info("{} - OUT - ReservationService | updateBooking()", booking);
        return booking;
    }

    @Transactional
    public BookingDTO cancelBooking(Integer id) throws ReservationNotFoundException, BookingCannotBeCancelledException {
        log.info("{} - IN - ReservationService | cancelBooking()", id);
        BookingDTO bookingDTO = this.getBookingById(id);
        bookingDTO.setStatus(BookingStatusEnum.CANCELED.toString());
        if (LocalDateTime.now().isEqual(bookingDTO.getStartTime().minusDays(1))) {
            throw new BookingCannotBeCancelledException("Booking with id " + id + " cannot be cancelled.");
        }
        Reservation booking = reservationRepository.save(bookingMapper.mapBookingDTOToEntity(bookingDTO));
        log.info("{} - OUT - ReservationService | cancelBooking()", booking);
        return bookingMapper.mapEntityToBookingDTO(booking);
    }

    @Transactional
    public BlockDTO createBlock(CreateBlockRequest request) throws InvalidStartTimeException, OverlappingReservationException {
        log.info("- IN - ReservationService | createBlock()");
        if (isOverlappingOnCreate(request.getStartTime(), request.getEndTime(), request.getPropertyId(), null)) {
            throw new OverlappingReservationException("Block can not be created because of overlapping dates.");
        }
        BlockDTO blockDTO = BlockDTO.builder().startTime(request.getStartTime()).endTime(request.getEndTime()).propertyId(request.getPropertyId()).status(BookingStatusEnum.BLOCKED.toString()).build();
        blockDTO.validateDates();
        Reservation block = reservationRepository.save(blockMapper.mapBlockDTOToEntity(blockDTO));
        log.info("{} - OUT - ReservationService | createBlock()", block);
        BlockDTO newBlockDto = blockMapper.mapEntityToBlockDTO(block);
        return newBlockDto;
    }

    public List<BlockDTO> getAllBlocks() {
        return reservationRepository.findAllByStatus(BookingStatusEnum.BLOCKED).stream()
                .map(blockMapper::mapEntityToBlockDTO).toList();
    }

    public BlockDTO getBlockById(Integer id) throws ReservationNotFoundException {
        return blockMapper.mapEntityToBlockDTO(reservationRepository.findReservationByIdAndStatus(id, BookingStatusEnum.BLOCKED).orElseThrow(() -> new ReservationNotFoundException("No block found with id: " + id)));
    }

    @Transactional
    public void removeBlock(Integer id) throws ReservationNotFoundException {
        log.info("{} - IN - ReservationService | removeBlock()", id);
        Reservation block = reservationRepository.findReservationByIdAndStatus(id, BookingStatusEnum.BLOCKED).orElseThrow(() -> new ReservationNotFoundException("No booking found with id: " + id));
        reservationRepository.delete(block);
        log.info("- OUT - ReservationService | removeBlock()");

    }

    private boolean isOverlappingOnCreate(LocalDateTime start, LocalDateTime end, Integer propertyId, Integer userId) throws OverlappingReservationException {
        multipleBooking(start, end, propertyId, userId);
        return reservationRepository.findAllByStatusNotAndPropertyId(BookingStatusEnum.CANCELED, propertyId).stream()
                .anyMatch(booking -> (start.isBefore(booking.getEndTime()) && end.isAfter(booking.getStartTime())));
    }

    private boolean isOverlappingOnUpdate(Integer bookingId, LocalDateTime start, LocalDateTime end, Integer propertyId, Integer userId) throws OverlappingReservationException {
        multipleBooking(start, end, propertyId, userId);
        return reservationRepository.findAllByStatusNotAndPropertyId(BookingStatusEnum.CANCELED, propertyId).stream()
                .anyMatch(booking -> (start.isBefore(booking.getEndTime()) && end.isAfter(booking.getStartTime()) && !booking.getId().equals(bookingId)));
    }

    private void multipleBooking(LocalDateTime start, LocalDateTime end, Integer propertyId, Integer userId) throws OverlappingReservationException {
        boolean check = reservationRepository.findAllByStatusNotAndProperty_IdNot(BookingStatusEnum.CANCELED, propertyId).stream()
                .anyMatch(booking -> (start.isBefore(booking.getEndTime()) && end.isAfter(booking.getStartTime())) && booking.getUser().getId().equals(userId));
        if (check) {
            throw new OverlappingReservationException("Booking can not be created because of overlapping dates on another property for the same user");
        }
    }
}
