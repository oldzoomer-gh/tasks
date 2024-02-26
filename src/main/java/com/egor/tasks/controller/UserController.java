package com.egor.tasks.controller;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.IncorrectPassword;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginAndRegistrationDto loginDTO) throws UserNotFound, IncorrectPassword {
        return userService.login(loginDTO);
    }

    @PostMapping("/reg")
    public void reg(@RequestBody LoginAndRegistrationDto userDTO) throws DuplicateUser {
        userService.reg(userDTO);
    }

}
