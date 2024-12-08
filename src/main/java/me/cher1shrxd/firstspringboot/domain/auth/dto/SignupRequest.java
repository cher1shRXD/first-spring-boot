package me.cher1shrxd.firstspringboot.domain.auth.dto;

public record SignupRequest(
        String username,
        String email,
        String password
) {
}
