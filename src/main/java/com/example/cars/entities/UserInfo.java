package com.example.cars.entities;

public class UserInfo {
    private Long userId;
    private String email;
    private String phone;

    public UserInfo(Long userId, String email, String phone) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
