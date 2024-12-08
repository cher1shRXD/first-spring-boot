package me.cher1shrxd.firstspringboot.domain.auth.dto;

public record LoginRespose(
        String accessToken,
        String refreshToken
) {
}
