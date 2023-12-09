package com.egor.tasks.service;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.IncorrectPassword;
import com.egor.tasks.exception.UserNotFound;

public interface UserService {
    String login(LoginAndRegistrationDto loginData) throws UserNotFound, IncorrectPassword;
    void reg(LoginAndRegistrationDto userData) throws DuplicateUser;
}
