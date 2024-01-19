package com.egor.tasks.service;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;

public interface UserService {
    String login(LoginAndRegistrationDto loginData);
    void reg(LoginAndRegistrationDto userData);
}
