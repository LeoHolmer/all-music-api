package com.leoholmer.AllMusic.backend.dto;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private String username;
    private String password;
}
