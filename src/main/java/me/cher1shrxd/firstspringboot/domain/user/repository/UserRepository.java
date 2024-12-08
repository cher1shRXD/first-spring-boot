package me.cher1shrxd.firstspringboot.domain.user.repository;

import me.cher1shrxd.firstspringboot.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
