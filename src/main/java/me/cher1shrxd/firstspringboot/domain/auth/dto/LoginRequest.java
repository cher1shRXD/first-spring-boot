package me.cher1shrxd.firstspringboot.domain.auth.dto;

public record LoginRequest(
        String username,
        String password
) {
}
