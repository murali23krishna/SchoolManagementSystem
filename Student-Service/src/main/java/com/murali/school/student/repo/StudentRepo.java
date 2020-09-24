package com.murali.school.student.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.murali.school.models.Student;
@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{
	Student findStudentByname(String name);
}
