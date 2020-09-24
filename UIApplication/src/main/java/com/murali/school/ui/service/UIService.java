package com.murali.school.ui.service;

import java.util.Optional;

import com.murali.school.models.OAuthClientDetails;
import com.murali.school.models.Student;
import com.murali.school.models.Teacher;
import com.murali.school.models.User;

public interface UIService {

	User save(User user);
	Optional<User> findUserByusername(String username);
	Teacher searchTeacher(Teacher teacher);
	Student searchStudent(Student student);
	Student createStudent(Student student);
	Teacher createTeacher(Teacher teacher);
	Student updateStudent(Student student);
	Teacher updateTeacher(Teacher teacher);
	User updateUser(User user);
	Student findStudentByName(String name);
	Teacher findTeacherByName(String name);
	Student findStudentById(int id);
	Teacher findTeacherById(int id);
	Optional<User> findUserById(int id);
	boolean deleteUser(User user);
	boolean deleteTeacher(Teacher teacher);
	boolean deleteStudent(Student student);
	OAuthClientDetails saveOAuthClient(OAuthClientDetails oauthClient);
	
}
