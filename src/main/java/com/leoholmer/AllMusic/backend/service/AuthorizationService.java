package com.leoholmer.AllMusic.backend.service;


import com.leoholmer.AllMusic.backend.model.User;

public interface AuthorizationService {
    User authorize(String token);
}