package com.leoholmer.AllMusic.backend.service;


import com.leoholmer.AllMusic.backend.model.User;

public interface UserService {
    User findByUsername(String username) throws Exception;
}