package com.hostfullly.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PropertyDTO {

    private Integer id;

    @NotEmpty(message = "Name can not be null or empty")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private String name;

    private String capacity;

    @NotEmpty(message = "Owner can not be null or empty")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Integer ownerId;
}
