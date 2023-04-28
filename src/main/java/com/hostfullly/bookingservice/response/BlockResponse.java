package com.hostfullly.bookingservice.response;

import com.hostfullly.bookingservice.dto.BlockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BlockResponse extends BaseResponse {
    BlockDTO blockDTO;
}
