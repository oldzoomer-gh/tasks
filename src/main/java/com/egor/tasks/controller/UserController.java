package com.egor.tasks.controller;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.IncorrectPassword;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getToken(@RequestBody LoginAndRegistrationDto loginDTO) throws UserNotFound, IncorrectPassword {
        return userService.login(loginDTO);
    }

    @PostMapping("/reg")
    public void postMethodName(@RequestBody LoginAndRegistrationDto userDTO) throws DuplicateUser {
        userService.reg(userDTO);
    }

}
