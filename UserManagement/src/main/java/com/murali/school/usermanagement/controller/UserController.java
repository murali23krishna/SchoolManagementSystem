package com.murali.school.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murali.school.models.User;
import com.murali.school.usermanagement.service.UserService;

@RestController
@RequestMapping("/userservice/api")
public class UserController {

	@Autowired
	UserService service;	
	

	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteUser(@PathVariable("id") int id) {
		this.service.delete(id);
	}

	@GetMapping("/user/{id}")
	public User findUser(@PathVariable("id") int id) {
		return this.service.find(id);
	}
	@PutMapping("/user")
	@PreAuthorize("hasAuthority('ADMIN')")
	public User updateUser(User user) {
		return this.service.update(user);
	}
	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

}
