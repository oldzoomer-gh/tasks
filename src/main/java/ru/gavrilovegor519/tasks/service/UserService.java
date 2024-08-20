package ru.gavrilovegor519.tasks.service;

import ru.gavrilovegor519.tasks.dto.output.users.TokenDto;
import ru.gavrilovegor519.tasks.entity.User;

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
