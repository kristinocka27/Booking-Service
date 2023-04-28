package com.hostfullly.bookingservice.repository;

import com.hostfullly.bookingservice.entity.Reservation;
import com.hostfullly.bookingservice.enums.BookingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findBookingByIdAndStatusIn(Integer id, List<BookingStatusEnum> statuses);

    List<Reservation> findAllByStatusNotIn(List<BookingStatusEnum> statuses);

    List<Reservation> findAllByStatusNotAndPropertyId(BookingStatusEnum status, Integer id);

    List<Reservation> findAllByStatusNotAndProperty_IdNot(BookingStatusEnum status, Integer id);

    List<Reservation> findAllByStatusNot(BookingStatusEnum status);

    List<Reservation> findAllByStatus(BookingStatusEnum status);

    Optional<Reservation> findReservationByIdAndStatus(Integer id, BookingStatusEnum status);
}
