package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.model.User;

public interface AuthenticationService {
    String authenticate(User user) throws Exception;
}