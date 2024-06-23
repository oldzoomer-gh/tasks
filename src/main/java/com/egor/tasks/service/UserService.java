package com.egor.tasks.service;

import com.egor.tasks.dto.output.users.TokenDto;
import com.egor.tasks.entity.User;

/**
 * User management service layer.
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface UserService {
    /**
     * Login user with email and password. Generates JWT Bearer token.
     * @param user Login data.
     * @return JWT Bearer token.
     */
    TokenDto login(User user);

    /**
     * Register user.
     * @param user Registration data.
     */
    void reg(User user);
}
