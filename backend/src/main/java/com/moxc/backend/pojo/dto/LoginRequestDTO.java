package com.moxc.backend.pojo.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
