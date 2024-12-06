package me.cher1shrxd.firstspringboot.domain.board.dto;

public record WriteRequest(
        String title,
        String detail,
        String author
) {
}
