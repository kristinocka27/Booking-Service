package com.hostfullly.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hostfullly.bookingservice.exception.InvalidStartTimeException;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private Integer id;

    @NotEmpty(message = "Start time can not be null or empty")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startTime;

    @NotEmpty(message = "End time can not be null or empty")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endTime;

    @NotEmpty(message = "Property can not be null or empty")
    private Integer propertyId;

    private String status;

    private Integer userId;

    public void validateDates() throws InvalidStartTimeException {
        if (endTime.isBefore(startTime) || endTime.getDayOfYear() == startTime.getDayOfYear()) {
            throw new InvalidStartTimeException("End date can not be before or equal to start date");
        }
    }
}
