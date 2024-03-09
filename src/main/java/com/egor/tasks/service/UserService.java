package com.egor.tasks.service;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.dto.output.TokenDto;

/**
 * User management service layer.
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface UserService {
    /**
     * Login user with email and password. Generates JWT Bearer token.
     * @param loginAndRegistrationDto Login data.
     * @return JWT Bearer token.
     */
    TokenDto login(LoginAndRegistrationDto loginAndRegistrationDto);

    /**
     * Register user.
     * @param loginAndRegistrationDto Registration data.
     */
    void reg(LoginAndRegistrationDto loginAndRegistrationDto);
}
