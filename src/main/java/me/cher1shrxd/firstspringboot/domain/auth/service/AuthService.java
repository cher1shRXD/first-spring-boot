package me.cher1shrxd.firstspringboot.domain.auth.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.auth.dto.LoginRequest;
import me.cher1shrxd.firstspringboot.domain.auth.dto.SignupRequest;
import me.cher1shrxd.firstspringboot.domain.auth.repository.RefreshTokenRepository;
import me.cher1shrxd.firstspringboot.domain.user.entity.UserEntity;
import me.cher1shrxd.firstspringboot.domain.auth.enums.UserRole;
import me.cher1shrxd.firstspringboot.domain.user.repository.UserRepository;
import me.cher1shrxd.firstspringboot.global.exception.CustomErrorCode;
import me.cher1shrxd.firstspringboot.global.exception.CustomException;
import me.cher1shrxd.firstspringboot.global.security.jwt.dto.JwtResponse;
import me.cher1shrxd.firstspringboot.global.security.jwt.provider.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public void signup(SignupRequest signupRequest) {
        String hashedPassword = bCryptPasswordEncoder.encode(signupRequest.password());

        if(userRepository.existsByUsername(signupRequest.username())) {
            throw new CustomException(CustomErrorCode.USERNAME_DUPLICATION);
        }

        if(userRepository.existsByEmail(signupRequest.email())) {
            throw new CustomException(CustomErrorCode.EMAIL_DUPLICATION);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(signupRequest.username())
                .email(signupRequest.email())
                .password(hashedPassword)
                .role(UserRole.USER)
                .build();

        userRepository.save(userEntity);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new CustomException(CustomErrorCode.WRONG_PASSWORD);

        JwtResponse token = jwtProvider.generateToken(username, user.getRole());

        refreshTokenRepository.save(username, token.refreshToken());

        return token;
    }

}
