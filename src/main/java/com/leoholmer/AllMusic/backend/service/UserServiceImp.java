package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.exception.UserNotFoundException;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}