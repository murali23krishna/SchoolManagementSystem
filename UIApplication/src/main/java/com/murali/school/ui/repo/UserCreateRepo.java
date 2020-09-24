package com.murali.school.ui.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murali.school.models.User;

public interface UserCreateRepo extends JpaRepository<User, Integer>{	
	Optional<User> findByusername(String name);	 
}
