package com.owary.springbootsecurityoauth2jwt.controller;

import com.owary.springbootsecurityoauth2jwt.model.User;
import com.owary.springbootsecurityoauth2jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List listUser(){
        return userService.findAll();
    }

    @PostMapping("/user")
    public User create(@RequestBody User user){
        return userService.save(user);
    }

    /***
     * Here @PreAuthorize annotation simply does the following
     * If the authorized user has no scope of 'delete', simply cannot use the method
     */
    @PreAuthorize("#oauth2.hasScope('delete')")
    @DeleteMapping(value = "/user/{id}")
    public String delete(@PathVariable(value = "id") Long id){
        userService.deleteById(id);
        return "success";
    }

}
