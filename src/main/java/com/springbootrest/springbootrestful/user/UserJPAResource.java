package com.springbootrest.springbootrestful.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserDaoService service;

    @Autowired
    private UserRepository userRepository;

    //retrieve all users
    @GetMapping(path = "/jpa/users")
    public List<User> fetchAllUsers(){

        return userRepository.findAll();
    }

    //retrieve a single user using user id
    @GetMapping(path = "/jpa/users/{id}")
    public Resource<User> fetchUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id-" + id + " User not found");

        //HATEOAS create a link to retrieve all users
        Resource<User> resource = new Resource<User>(user.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).fetchAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    //creating a new user
    @PostMapping(path = "/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    //delete a single user using user id
    @DeleteMapping(path = "/jpa/users/{id}")
    public String deleteUser(@PathVariable int id){
        User user = service.deleteUserById(id);
        if (user == null)
            throw new UserNotFoundException("id-" + id);
        return user + " deleted successfully";
    }
}
