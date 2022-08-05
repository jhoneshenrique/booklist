package com.jhones.booklist.service.implementation;

import com.jhones.booklist.model.User;
import com.jhones.booklist.repository.UserRepository;
import com.jhones.booklist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
