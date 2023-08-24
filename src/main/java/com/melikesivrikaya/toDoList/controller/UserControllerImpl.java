package com.melikesivrikaya.toDoList.controller;

import com.melikesivrikaya.toDoList.model.User;
import com.melikesivrikaya.toDoList.service.UserService;
import com.melikesivrikaya.toDoList.service.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserControllerImpl implements UserController{
    private UserService userService;
    @GetMapping
    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @GetMapping(path = "user")
    @Override
    public Optional<User> getUser(@RequestParam(name = "id") Long id){
        return userService.getUser(id);
    }
    @DeleteMapping(path = "user")
    @Override
    public void deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
    }
    @PostMapping
    @Override
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @PutMapping
    @Override
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}