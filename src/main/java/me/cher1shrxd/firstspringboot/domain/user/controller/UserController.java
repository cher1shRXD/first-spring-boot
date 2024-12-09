package me.cher1shrxd.firstspringboot.domain.user.controller;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.user.dto.UpdateRequest;
import me.cher1shrxd.firstspringboot.domain.user.dto.UserResponse;
import me.cher1shrxd.firstspringboot.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getMe() {
        return userService.getMe();
    }

    @PatchMapping("/me")
    public UserResponse updateMe(@RequestBody UpdateRequest updateRequest) {
        System.out.println(updateRequest);
        return userService.updateMe(updateRequest);
    }
}
