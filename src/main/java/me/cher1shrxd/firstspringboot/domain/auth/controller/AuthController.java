package me.cher1shrxd.firstspringboot.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.auth.dto.SignupRequest;
import me.cher1shrxd.firstspringboot.domain.auth.service.AuthService;
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
}
