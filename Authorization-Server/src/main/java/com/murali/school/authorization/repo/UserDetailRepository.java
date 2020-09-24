package com.murali.school.authorization.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murali.school.models.User;

public interface UserDetailRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String name);
}
