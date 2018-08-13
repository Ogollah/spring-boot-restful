package com.springbootrest.springbootrestful.user;


//user resource controller

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    //retrieve all users
    @GetMapping(path = "/users")
    public List<User> fetchAllUsers(){
        return service.getAllUsers();
    }

    //retrieve a single user using user id
    @GetMapping(path = "/users/{id}")
    public User fetchUser(@PathVariable int id){
        return service.findUser(id);
    }
}
