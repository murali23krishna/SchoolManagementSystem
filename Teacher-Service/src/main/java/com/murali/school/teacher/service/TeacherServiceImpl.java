package com.murali.school.teacher.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murali.school.models.Teacher;
import com.murali.school.teacher.repo.TeacherRepo;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	TeacherRepo repo;

	@Override
	public Teacher save(Teacher teacher) {
		Teacher teacherFromDB = this.findTeacherByname(teacher.getName());
		if(teacherFromDB!=null && teacherFromDB.getName().equalsIgnoreCase(teacher.getName())) {
			teacher.setErrorMessage("The teacher name is already exist..Please choose other name");
			return teacher;
		}
		return this.repo.save(teacher);
	}

	@Override
	public Teacher update(Teacher teacher) {
		Teacher teacherFromDB = this.find(teacher.getId());
		if (teacherFromDB.getId() != null) {
			return this.repo.save(teacher);
		} else {
			return null;
		}
	}

	@Override
	public void delete(int id) {
		Teacher teacherFromDB = this.find(id);
		if (teacherFromDB.getId() != null) {
			this.repo.delete(teacherFromDB);
		}
	}

	@Override
	public Teacher find(int id) {
		Optional<Teacher> teacher = this.repo.findById(id);
		if (teacher.isPresent()) {
			return teacher.get();
		} else {
			return null;
		}
	}

	public TeacherRepo getRepo() {
		return repo;
	}

	public void setRepo(TeacherRepo repo) {
		this.repo = repo;
	}

	@Override
	public Teacher findTeacherByname(String name) {
		return this.repo.findTeacherByname(name);
	}

}
