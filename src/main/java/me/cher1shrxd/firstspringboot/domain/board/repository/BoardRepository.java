package me.cher1shrxd.firstspringboot.domain.board.repository;

import me.cher1shrxd.firstspringboot.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByOrderByIdDesc();
}
