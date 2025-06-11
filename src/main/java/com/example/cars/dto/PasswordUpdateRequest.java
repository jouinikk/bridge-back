package com.example.cars.dto;

import lombok.Getter;

@Getter
public class PasswordUpdateRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
