package com.murali.school.student.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murali.school.models.Student;
import com.murali.school.student.repo.StudentRepo;
@Service
public class StudentServiceImpl implements StudentService{

	
	@Autowired
	StudentRepo repo;
	
	@Override
	public Student save(Student student) {
		Student studentFromDB=this.findStudentByName(student.getName());
		if(studentFromDB!=null && studentFromDB.getName().equalsIgnoreCase(student.getName())) {
			student.setErrorMessage("Student name: "+student.getName()+" is already exist, please choose other");
			return student;
		}
		return this.repo.save(student);
	}

	@Override
	public Student update(Student student) {
		if(student.getId()!=null) {
			Student studentFromDB=this.find(student.getId());
			if(studentFromDB!=null && studentFromDB.getId()!=null) {
				studentFromDB.setAddress(student.getAddress());
				studentFromDB.setAge(student.getAge());
				studentFromDB.setId(student.getId());
				studentFromDB.setName(student.getName());
				studentFromDB.setStudentClass(student.getStudentClass());
				return this.repo.save(student);
			}
		}
		
		return null;
	}

	@Override
	public void delete(int id) {
		Student student=this.find(id);
		if(student!=null) {
			this.repo.delete(student);
		}else {
			System.out.println("Student not found for the given id");
		}
	}

	@Override
	public Student find(int id) {
		Optional<Student> student= this.repo.findById(id);
		if(student.isPresent()) {
			return student.get();
		}else {
			return null;
		}
	}

	public StudentRepo getRepo() {
		return repo;
	}

	public void setRepo(StudentRepo repo) {
		this.repo = repo;
	}

	@Override
	public Student findStudentByName(String name) {
		return this.repo.findStudentByname(name);
	}
	
	

}
