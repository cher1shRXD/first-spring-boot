package me.cher1shrxd.firstspringboot.domain.board.dto;

import me.cher1shrxd.firstspringboot.domain.board.entity.BoardEntity;

public record PostResponse(
        Long id,
        String title,
        String detail,
        String author
) {
    public static PostResponse of(BoardEntity boardEntity) {
        return new PostResponse(boardEntity.getId(), boardEntity.getTitle(), boardEntity.getDetail(), boardEntity.getAuthor());
    }
}
