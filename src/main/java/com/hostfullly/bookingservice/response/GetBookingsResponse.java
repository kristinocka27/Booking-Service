package com.hostfullly.bookingservice.response;

import com.hostfullly.bookingservice.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class GetBookingsResponse extends BaseResponse {
    List<BookingDTO> bookingDTOList;
}
