package com.springbootrest.springbootrestful.user;


//user resource controller
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public Resource<User> fetchUser(@PathVariable int id){
        User user = service.findUser(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);

        //HATEOAS create a link to retrieve all users
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).fetchAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    //creating a new user
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.saveUser(user);
      URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}").buildAndExpand(savedUser.getId()).toUri();

      return ResponseEntity.created(location).build();
    }
    //delete a single user using user id
    @DeleteMapping(path = "/users/{id}")
    public String deleteUser(@PathVariable int id){
        User user = service.deleteUserById(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        return user + " deleted successfully";
    }
}
