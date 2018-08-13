package com.springbootrest.springbootrestful.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {

    //Simple user list
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 5;


    static{
        users.add(new User(1, "Wesley", new Date()));
        users.add(new User(2, "Moses", new Date()));
        users.add(new User(3, "Noel", new Date()));
        users.add(new User(4, "Abdi", new Date()));
        users.add(new User(5, "Lynnet", new Date()));
    }


    //Get a list of all users
    public List<User> getAllUsers(){
        return users;
    }
    //Save a new user
    public User saveUser(User user){
        if(user.getId()==null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }
    //Get a single user by use of user id
    public User findUser(int id){
        for (User user:users){
            if (user.getId()==id){
                return user;
            }
        }
        return null;
    }
}
