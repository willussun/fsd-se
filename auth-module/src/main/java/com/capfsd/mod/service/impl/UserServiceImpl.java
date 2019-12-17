package com.capfsd.mod.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capfsd.mod.repository.UserRepository;
import com.capfsd.mod.entity.User;
import com.capfsd.mod.dto.UserDto;
import com.capfsd.mod.service.UserService;
import util.MailUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    @Override
    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userRepository.save(user);
        }
        return userDto;
    }
	@Override
	public UserDto convert(User user) {
		UserDto userDto = new UserDto();
		userDto.setPassword(user.getPassword());
		userDto.setPhoneNo(user.getPhoneNo());
		userDto.setStatus(user.getStatus());
		userDto.setUsername(user.getUsername());
		userDto.setLastName(user.getLastName());
		userDto.setFirstName(user.getLastName());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

    @Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		newUser.setPhoneNo(user.getPhoneNo());
		newUser.setStatus(user.getStatus());
		newUser.setRole("USER");
        return userRepository.save(newUser);
    }

	@Override
	public Integer updateUserByIdWithStatus(Long id, Integer status) {
		return userRepository.updateUserByIdWithStatus(id, status);
	}

	@Override
	public User createUser(UserDto userDto) throws Exception {
		String email = userDto.getEmail();
		User user = this.save(userDto);
		Long id = user.getId();
		MailUtils.sendEmail(email, "Please activate your registration", "http://localhost:8090/auth-module/activate/" + user.getId());
		return user;
	}
}
