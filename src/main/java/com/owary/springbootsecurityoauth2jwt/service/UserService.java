package com.owary.springbootsecurityoauth2jwt.service;

import com.owary.springbootsecurityoauth2jwt.model.User;

import java.util.List;

public interface UserService{

    User save(User user);
    List<User> findAll();
    void deleteById(Long id);

}
