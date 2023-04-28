package com.hostfullly.bookingservice.response;

import com.hostfullly.bookingservice.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookingResponse extends BaseResponse {
    BookingDTO bookingDTO;
}
