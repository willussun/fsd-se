package com.capfsd.mod.controller;

import com.capfsd.mod.dto.UserDto;
import com.capfsd.mod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.ResponseResult;

import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/activate")
public class ValidController {
    @Autowired
    public UserService userService;
    @GetMapping(value = "/{id}")
    public ResponseResult ActivateByIdWithStatus(@PathVariable(value = "id") Long id) {
        int result = userService.updateUserByIdWithStatus(id, 1);
        HashMap hashMap = new HashMap();
        hashMap.put("result", result);
        return ResponseResult.success("successfully activate the user", hashMap, null);
    }
}
