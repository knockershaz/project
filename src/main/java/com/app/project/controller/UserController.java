package com.app.project.controller;

import com.app.project.exception.UserNotFound;
import com.app.project.model.User;
import com.app.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://project-frontend-one.vercel.app")
public class UserController {
    @Autowired
    UserRepository ur;

    @PostMapping("/user/save")
    public User newUser(@RequestBody User u){
        return ur.save(u);
    }
    @GetMapping("/users")
    public List<User> getAllUser(){
        return ur.findAll();
    }

    @GetMapping("/user/{id}")
    public User getByID(@PathVariable(value = "id") Long id){
        return ur.findById(id).orElseThrow(()-> new UserNotFound(id));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return ur.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return ur.save(user);
                }).orElseThrow(() -> new UserNotFound(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id){
        if(!ur.existsById(id)) throw new UserNotFound(id);
        ur.deleteById(id);
        return "User with id "+id+" deleted successfully";
    }
}
