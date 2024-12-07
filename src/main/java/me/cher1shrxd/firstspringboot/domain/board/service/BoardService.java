package me.cher1shrxd.firstspringboot.domain.board.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.board.dto.PostResponse;
import me.cher1shrxd.firstspringboot.domain.board.dto.UpdateRequest;
import me.cher1shrxd.firstspringboot.domain.board.dto.WriteRequest;
import me.cher1shrxd.firstspringboot.domain.board.entity.BoardEntity;
import me.cher1shrxd.firstspringboot.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void writePost(WriteRequest writeRequest) {
        BoardEntity boardEntity = BoardEntity.builder()
                .title(writeRequest.title())
                .detail(writeRequest.detail())
                .author(writeRequest.author())
                .build();

        boardRepository.save(boardEntity);
    }

    public List<PostResponse> getPosts() {
        List<BoardEntity> board = boardRepository.findAllByOrderByIdDesc();
        return board.stream().map(PostResponse::of).toList();
    }

    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    public void updatePost(Long id, UpdateRequest updateRequest) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        if(updateRequest.title() != null) boardEntity.setTitle(updateRequest.title());
        if(updateRequest.detail() != null) boardEntity.setDetail(updateRequest.detail());
        boardRepository.save(boardEntity);
    }

    public PostResponse getPostById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        return PostResponse.of(boardEntity);
    }
}
