package me.cher1shrxd.firstspringboot.domain.auth.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.auth.dto.SignupRequest;
import me.cher1shrxd.firstspringboot.domain.auth.entity.UserEntity;
import me.cher1shrxd.firstspringboot.domain.auth.enums.UserRole;
import me.cher1shrxd.firstspringboot.domain.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(SignupRequest signupRequest) {
        String hashedPassword = bCryptPasswordEncoder.encode(signupRequest.password());

        UserEntity userEntity = UserEntity.builder()
                .username(signupRequest.username())
                .email(signupRequest.email())
                .password(hashedPassword)
                .role(UserRole.USER)
                .build();

        userRepository.save(userEntity);
    }
}
