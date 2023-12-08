package com.egor.tasks.service;

import com.egor.tasks.dto.LoginAndRegistrationDto;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.UserNotFound;

public interface UserService {
    String login(LoginAndRegistrationDto loginData) throws UserNotFound;
    void reg(LoginAndRegistrationDto userData) throws DuplicateUser;
}
