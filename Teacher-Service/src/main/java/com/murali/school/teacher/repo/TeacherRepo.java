package com.murali.school.teacher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.murali.school.models.Teacher;
@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Integer>{

	Teacher findTeacherByname(String name);
}
