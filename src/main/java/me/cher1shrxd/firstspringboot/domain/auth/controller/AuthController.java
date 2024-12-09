package me.cher1shrxd.firstspringboot.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.auth.dto.LoginRequest;
import me.cher1shrxd.firstspringboot.domain.auth.dto.ReissueRequest;
import me.cher1shrxd.firstspringboot.domain.auth.dto.SignupRequest;
import me.cher1shrxd.firstspringboot.domain.auth.service.AuthService;
import me.cher1shrxd.firstspringboot.global.security.jwt.dto.JwtResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest) {
            authService.signup(signupRequest);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/reissue")
    public JwtResponse reissue(@RequestBody ReissueRequest reissueRequest) {
        return authService.reissue(reissueRequest);
    }
}
