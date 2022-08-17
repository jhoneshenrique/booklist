package com.jhones.booklist.service;

import com.jhones.booklist.model.User;

public interface UserService {
    User findByLogin(String login);

    User save(User user);
}
