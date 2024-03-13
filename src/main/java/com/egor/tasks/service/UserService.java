package com.egor.tasks.service;

import com.egor.tasks.dto.TokenDto;
import com.egor.tasks.dto.UserDto;

/**
 * User management service layer.
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface UserService {
    /**
     * Login user with email and password. Generates JWT Bearer token.
     * @param userDto Login data.
     * @return JWT Bearer token.
     */
    TokenDto login(UserDto userDto);

    /**
     * Register user.
     * @param userDto Registration data.
     */
    void reg(UserDto userDto);
}
