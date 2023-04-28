package com.hostfullly.bookingservice.response;

import com.hostfullly.bookingservice.dto.BlockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class GetBlocksResponse extends BaseResponse {
    List<BlockDTO> blockDTOList;
}
