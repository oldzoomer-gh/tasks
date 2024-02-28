package com.egor.tasks.service;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.dto.output.TokenDto;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.IncorrectPassword;
import com.egor.tasks.exception.UserNotFound;

public interface UserService {
    TokenDto login(LoginAndRegistrationDto loginData) throws IncorrectPassword, UserNotFound;
    void reg(LoginAndRegistrationDto userData) throws DuplicateUser;
}
