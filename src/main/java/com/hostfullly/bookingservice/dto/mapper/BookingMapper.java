package com.hostfullly.bookingservice.dto.mapper;

import com.hostfullly.bookingservice.dto.BookingDTO;
import com.hostfullly.bookingservice.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "property.id", target = "propertyId")
    BookingDTO mapEntityToBookingDTO(Reservation booking);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "propertyId", target = "property.id")
    Reservation mapBookingDTOToEntity(BookingDTO bookingDTO);
}
