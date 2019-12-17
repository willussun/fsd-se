package com.capfsd.mod.controller;

import com.capfsd.mod.config.JwtTokenUtil;
import com.capfsd.mod.dto.AuthToken;
import com.capfsd.mod.dto.LoginUser;
import com.capfsd.mod.entity.User;
import com.capfsd.mod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import util.LogUtils;
import util.ResponseResult;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody LoginUser loginUser) throws AuthenticationException {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            final User user = userService.findOne(loginUser.getUsername());
            final String token = jwtTokenUtil.generateToken(user);
            LogUtils.getInst(this).info("Role is:" + user.getRole());

            // If status is 0, means the user hasn't activated the registration, only the status is changed to 1 by email confirmation will let the
            // verification to be success.
            if (user.getStatus() == 0) {
                return ResponseResult.fail("Please activate your registration first", null);

            }
            return ResponseResult.success("Authentication success", new AuthToken(token, user.getUsername(), user.getRole()), null);
        }
        catch(Exception e)
        {
            LogUtils.getInst(this).error(e.getMessage());
            return ResponseResult.fail("Please enter correct name and password", null);
        }
    }

}
