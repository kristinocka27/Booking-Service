package com.hostfullly.bookingservice.manager;

import com.hostfullly.bookingservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingManager {
    private final ReservationService bookingService;

}
