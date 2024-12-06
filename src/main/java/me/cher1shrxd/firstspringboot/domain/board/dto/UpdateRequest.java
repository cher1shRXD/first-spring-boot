package me.cher1shrxd.firstspringboot.domain.board.dto;

public record UpdateRequest(
        String title,
        String detail
) {
}