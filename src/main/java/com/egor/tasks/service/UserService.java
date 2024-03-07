package com.egor.tasks.service;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.dto.output.TokenDto;

public interface UserService {
    TokenDto login(LoginAndRegistrationDto loginData);
    void reg(LoginAndRegistrationDto userData);
}
