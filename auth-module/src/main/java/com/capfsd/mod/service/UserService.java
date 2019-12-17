package com.capfsd.mod.service;

import java.util.List;

import com.capfsd.mod.entity.User;
import com.capfsd.mod.dto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(Long id);

    User findOne(String username);

    User findById(Long id);

    UserDto update(UserDto userDto);

    Integer updateUserByIdWithStatus(Long id, Integer status);

    User createUser(UserDto userDto) throws Exception;

    UserDto convert(User user);
}
