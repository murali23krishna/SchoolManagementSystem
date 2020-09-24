package com.murali.school.usermanagement.service;

import java.util.Optional;

import com.murali.school.models.User;

public interface UserService {

	User update(User user);
	void delete(int id);
	User find(int id);
	Optional<User> findbyUserName(String name);
}
