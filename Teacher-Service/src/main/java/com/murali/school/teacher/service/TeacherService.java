package com.murali.school.teacher.service;

import com.murali.school.models.Teacher;


public interface TeacherService {

	Teacher save(Teacher teacher);
	Teacher update(Teacher teacher);
	void delete(int id);
	Teacher find(int id);
	
}
