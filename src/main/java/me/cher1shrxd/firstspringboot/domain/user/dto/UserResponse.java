package me.cher1shrxd.firstspringboot.domain.user.dto;

import lombok.*;
import me.cher1shrxd.firstspringboot.domain.auth.enums.UserRole;
import me.cher1shrxd.firstspringboot.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private UserRole role;

    public UserResponse toUser(UserEntity userEntity){
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .nickname(userEntity.getNickname())
                .build();
    }
}
