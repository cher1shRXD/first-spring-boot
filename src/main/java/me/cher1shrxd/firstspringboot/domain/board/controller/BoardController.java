package me.cher1shrxd.firstspringboot.domain.board.controller;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.board.dto.PostResponse;
import me.cher1shrxd.firstspringboot.domain.board.dto.UpdateRequest;
import me.cher1shrxd.firstspringboot.domain.board.dto.WriteRequest;
import me.cher1shrxd.firstspringboot.domain.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping()
    public void writePost(@RequestBody WriteRequest writeRequest) {
        boardService.writePost(writeRequest);
    }

    @GetMapping()
    public List<PostResponse> getPosts() {
        return boardService.getPosts();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        boardService.deletePost(id);
    }

    @PatchMapping("/{id}")
    public void updatePost(@PathVariable("id") Long id, @RequestBody UpdateRequest updateRequest) {
        boardService.updatePost(id, updateRequest);
    }
}
