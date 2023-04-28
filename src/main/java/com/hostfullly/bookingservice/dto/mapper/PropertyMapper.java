package com.hostfullly.bookingservice.dto.mapper;

import com.hostfullly.bookingservice.dto.PropertyDTO;
import com.hostfullly.bookingservice.entity.Property;
import org.mapstruct.Mapping;

public interface PropertyMapper {
    @Mapping(source = "user.id", target = "ownerId")
    PropertyDTO mapEntityToPropertyDTO(Property property);

    @Mapping(source = "ownerId", target = "user.id")
    Property mapPropertyDTOToEntity(PropertyDTO propertyDTO);
}
