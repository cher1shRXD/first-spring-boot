package me.cher1shrxd.firstspringboot.domain.auth.repository;

import me.cher1shrxd.firstspringboot.domain.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
