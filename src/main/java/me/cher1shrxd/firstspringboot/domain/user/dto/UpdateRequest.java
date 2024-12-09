package me.cher1shrxd.firstspringboot.domain.user.dto;

public record UpdateRequest(
        String email,
        String nickname,
        String password,
        String currentPassword
) {
}
