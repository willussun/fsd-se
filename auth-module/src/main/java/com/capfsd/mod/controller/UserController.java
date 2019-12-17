package com.capfsd.mod.controller;

import com.capfsd.mod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capfsd.mod.entity.User;
import com.capfsd.mod.dto.UserDto;
import util.ResponseResult;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseResult<User> saveUser(@RequestBody UserDto user) throws Exception {
        User user1 = userService.createUser(user);
        return ResponseResult.success("User saved successfully", user1, null);
    }

    @GetMapping
    public List<User> listUser(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseResult<User> getOne(@PathVariable Long id){
        return ResponseResult.success("User fetched successfully",userService.findById(id),null);
    }

    @PutMapping("/{id}")
    public ResponseResult<UserDto> update(@RequestBody UserDto userDto) {
        return ResponseResult.success("User updated successfully.",userService.update(userDto), null);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseResult.success("User deleted successfully.", null, null);
    }
}
