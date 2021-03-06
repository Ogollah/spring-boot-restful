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
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

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
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    //delete a single user using user id
    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    //retrieve all posts of a user
    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> fetchAllPosts(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()){
            throw new UserNotFoundException("id-" + id);
        }
        return userOptional.get().getPosts();
    }

    //create a post
    @PostMapping(path = "/jpa/users/{id}/post")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            throw new UserNotFoundException("id-" + id);
        }

        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    //delete a post
    @DeleteMapping(path = "/jpa/users/{id}/posts/{postId}")
    public void deleteAPost(@PathVariable("id") int id, @PathVariable("postId") int postId){
        Optional<User>userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }
        User user = userOptional.get();
        user.setId(id);
        postRepository.deleteById(postId);
    }
}
