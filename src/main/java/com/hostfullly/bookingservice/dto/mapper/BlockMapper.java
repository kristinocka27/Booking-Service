package com.hostfullly.bookingservice.dto.mapper;


import com.hostfullly.bookingservice.dto.BlockDTO;
import com.hostfullly.bookingservice.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlockMapper {
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "status", target = "status")
    BlockDTO mapEntityToBlockDTO(Reservation block);

    @Mapping(source = "propertyId", target = "property.id")
    Reservation mapBlockDTOToEntity(BlockDTO blockDTO);
}
