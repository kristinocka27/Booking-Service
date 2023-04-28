package com.hostfullly.bookingservice.dto;

import com.hostfullly.bookingservice.enums.UserRoleEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

    private Integer id;

    private String name;

    @NotEmpty(message = "E-mail can not be null or empty")
    private String email;

    @NotEmpty(message = "User role can not be null or empty")
    private UserRoleEnum role;
}

