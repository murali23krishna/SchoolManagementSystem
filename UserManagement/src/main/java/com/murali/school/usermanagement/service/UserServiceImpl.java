package com.murali.school.usermanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murali.school.models.User;
import com.murali.school.usermanagement.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo repo;
	
	@Override
	public User update(User user) {
		return this.repo.save(user);
	}

	@Override
	public void delete(int id) {
		User userFromDB = this.find(id);
		if (userFromDB != null && userFromDB.getId() != null) {
			this.repo.deleteById(userFromDB.getId());
		}

	}

	@Override
	public User find(int id) {
		Optional<User> user = this.repo.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public UserRepo getRepo() {
		return repo;
	}

	public void setRepo(UserRepo repo) {
		this.repo = repo;
	}


	@Override
	public Optional<User> findbyUserName(String name) {
		return this.repo.findByusername(name);
	}

}
