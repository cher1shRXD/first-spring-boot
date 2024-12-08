package me.cher1shrxd.firstspringboot.global.security.jwt.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
