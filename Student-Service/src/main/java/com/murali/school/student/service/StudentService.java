package com.murali.school.student.service;

import com.murali.school.models.Student;

public interface StudentService {

	Student save(Student student);
	Student update(Student student);
	void delete(int id);
	Student find(int id);
	Student findStudentByName(String name);
}
