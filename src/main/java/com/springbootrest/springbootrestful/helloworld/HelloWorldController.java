package com.springbootrest.springbootrestful.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloWorldController {

    //GET Method
    //URL - /hello-world
    //METHOD - "Hello World"
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World Bean!");
    }
    //hello-world/path-variable/{name}
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
